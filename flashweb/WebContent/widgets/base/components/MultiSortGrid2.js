Ext.define('Base.components.MultiSortGrid2', {
	extend: 'Ext.grid.Panel',
	requires: ['Ext.grid.feature.GroupingSummary'],
	xtype: 'multisortgrid2',
	viewConfig: {
		stripeRows: false,
		enableTextSelection: false,
		listeners: {
			refresh: function(dataview) {
				Ext.each(dataview.panel.columns, function (column) {
					if (column.autoSizeColumn === true) {
						column.autoSize();
					}
				});
			}
		}
	},

    features: [{
        id: 'reportGrouping',
        ftype: 'groupingsummary',
        groupHeaderTpl: '{columnName}: {name} ({rows.length} Item{[values.rows.length > 1 ? "s" : ""]})',
        hideGroupedHeader: true,
        
    }],
	stateful: true,
	columnLines: true,
	defaults: {
		sortable: true
	},
	sortableColumns: true,
	columns: [],

	initComponent: function(args) {

		var droppable = Ext.create('Ext.ux.ToolbarDroppable', {
            /**
             * Creates the new toolbar item from the drop event
             */
            createItem: function(data) {
                var header = data.header,
                    headerCt = header.ownerCt,
                    reorderer = headerCt.reorderer;
                
                // Hide the drop indicators of the standard HeaderDropZone
                // in case user had a pending valid drop in 
                if (reorderer) {
                    reorderer.dropZone.invalidateDrop();
                }

                return this.toolbar.up('grid').createSorterButtonConfig({
                    text: header.text,
                    sortData: {
                        property: header.dataIndex,
                        direction: "ASC"
                    }
                });
            },

            /**
             * Custom canDrop implementation which returns true if a column can be added to the toolbar
             * @param {Object} data Arbitrary data from the drag source. For a HeaderContainer, it will
             * contain a header property which is the Header being dragged.
             * @return {Boolean} True if the drop is allowed
             */
            canDrop: function(dragSource, event, data) {
            	var grid = this.toolbar.up('grid');
                var sorters = grid.getSorters(),
                    header  = data.header,
                    length = sorters.length,
                    entryIndex = this.calculateEntryIndex(event),
                    targetItem = this.toolbar.getComponent(entryIndex),
                    i;

                // Group columns have no dataIndex and therefore cannot be sorted
                // If target isn't reorderable it could not be replaced
                if (!header.dataIndex || (targetItem && targetItem.reorderable === false)) {
                    return false;
                }

                for (i = 0; i < length; i++) {
                	if (sorters[i] !== undefined) {
                		if (sorters[i].property == header.dataIndex) {
                            return false;
                        }
                	}
                }
                return true;
            },

            afterLayout: function() {
            	var grid = this.toolbar.up('grid');
            	grid.doSort();
            }
        });
		
		this.tbar = {
				itemId: 'sortBar',
		        items  : [{xtype: 'displayfield', itemId: 'fakeSpaceField'}],
		        plugins: [
		                  Ext.create('Ext.ux.BoxReorderer', {
				            listeners: {
				                scope: this,
				                Drop: function(r, c, button) { //update sort direction when button is dropped
				                	this.changeSortDirection(button, false);
				                }
				            }
				        }), 
				        droppable
				        ]
		};
		
        this.listeners = {
            afterlayout: {
                scope: this,
                // wait for the first layout to access the headerCt (we only want this once):
                single: true,
                // tell the toolbar's droppable plugin that it accepts items from the columns' dragdrop group
            	fn: function(grid) {
	                var headerCt = grid.child("headercontainer");
	                droppable.addDDGroup(headerCt.reorderer.dragZone.ddGroup);
//	                this.doSort();
                }
            },
	        staterestore: {
	            scope: this,
	        	fn: function(grid, state) {
	        		if(state.storeState !== undefined && state.storeState.sorters !== undefined) {
		        		var buttonArray = [];
		        		Ext.each(state.storeState.sorters, function(sorter) {
		        			var column = this.down('[dataIndex='+sorter.property+']');
		        			buttonArray.push(this.createSorterButtonConfig({
		                        text: column.text,
		                        sortData: {
		                            property: sorter.property,
		                            direction: sorter.direction
		                        }
		                    }));
		        		} , this);
		        		droppable.toolbar.add(buttonArray);
	        		}
	            }
	        }
        };

		this.callParent(args);
	},

	   //The following functions are used to get the sorting data from the toolbar and apply it to the store
	
    /**
     * Tells the store to sort itself according to our sort data
     */
    doSort: function() {
    	sorters = this.getSorters();
    	if(sorters.length) {
    		this.getStore().sort(this.getSorters());
    	} else {
    		this.getStore().sorters.clear();
    		this.getStore().sort();
    	}
    	
    	// Save the sort
    	this.saveState();
    },

    /**
     * Callback handler used when a sorter button is clicked or reordered
     * @param {Ext.Button} button The button that was clicked
     * @param {Boolean} changeDirection True to change direction (default). Set to false for reorder
     * operations as we wish to preserve ordering there
     */
    changeSortDirection: function(button, changeDirection) {
        var sortData = button.sortData,
            iconCls  = button.iconCls;
        
        if (sortData) {
            if (changeDirection !== false) {
                button.sortData.direction = Ext.String.toggle(button.sortData.direction, "ASC", "DESC");
                button.setIconCls(Ext.String.toggle(iconCls, "sort-asc", "sort-desc"));
            }
            this.getStore().clearFilter();
            this.doSort();
        }
    },

    /**
     * Returns an array of sortData from the sorter buttons
     * @return {Array} Ordered sort data from each of the sorter buttons
     */
    getSorters: function() {
        var sorters = [];
 
        Ext.each(this.getDockedItems('#sortBar')[0].query('button'), function(button) {
            sorters.push(button.sortData);
        }, this);

        return sorters;
    },

    /**
     * Convenience function for creating Toolbar Buttons that are tied to sorters
     * @param {Object} config Optional config object
     * @return {Object} The new Button configuration
     */
    createSorterButtonConfig: function(config) {
        var me = this;
        config = config || {};
        Ext.applyIf(config, {
            listeners: {
                click: function(button, e) {
                    me.changeSortDirection(button, true);
                }
            },
            iconCls: 'sort-' + config.sortData.direction.toLowerCase(),
            reorderable: true,
            xtype: 'splitbutton',
            arrowTooltip: 'Remove sort',
            arrowHandler: function(btn) {
            	var toolbar = btn.up('#sortBar');
            	toolbar.remove(btn);
            	var grid = toolbar.up('grid');
            	grid.doSort(); // this is important to occur for saving state
            }
        });
        return config;
    }
});