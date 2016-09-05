
package com.inalab;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.inalab.dao.TestDepartmentDaoImpl;
import com.inalab.dao.TestEmployeeDaoImpl;
import com.inalab.dao.TestKudosDaoImpl;
import com.inalab.dao.TestLoginDaoImpl;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	TestLoginDaoImpl.class,
	TestEmployeeDaoImpl.class,
	TestDepartmentDaoImpl.class,
	TestKudosDaoImpl.class

})
public class AllTests {

}
