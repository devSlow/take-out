package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 新增员工信息
     * @param employee
     * @author Slow
     * @CurrentTime 2024年7月29日15:39:06
     */
    void insertEmployee(Employee employee);

    /**
     * 员工信息分页查询
     *
     * @param employeePageQueryDTO
     * @return
     * @author Slow
     * @CurrentTime 2024年7月29日15:39:48
     */
    Page<Employee> pageQueryEmployee(EmployeePageQueryDTO employeePageQueryDTO);



    /**
     * 启用禁用员工账号
     *
     * @param employee
     * @author Slow
     * @currentTime 2024年7月29日17:09:10
     */
    void update(Employee employee);



    /**
     * 根据id查询员工信息
     *
     * @param id
     * @return
     */
    Employee QueryEmployeeById(Long id);
}
