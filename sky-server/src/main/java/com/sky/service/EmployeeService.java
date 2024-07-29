package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工信息
     *
     * @param employeeDTO
     * @author Slow
     * @currentTime 2024年7月29日12:20:01
     */
    void insertEmployee(EmployeeDTO employeeDTO);

    /**
     * 员工信息分页查询
     *
     * @param employeePageQueryDTO
     * @return
     * @author Slow
     * @CurrentTime 2024年7月29日15:35:31
     */
    PageResult pageQueryEmployee(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工账号
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 更新员工信息
     *
     * @param employeeDTO
     * @author Slow
     * @currentTime 2024年7月29日19:28:14
     */
    void updateEmployee(EmployeeDTO employeeDTO);

    /**
     * 根据id查询员工信息
     *
     * @param id
     * @return
     * @author Slow
     * @currentTime 2024年7月29日20:08:21
     */
    Employee QueryEmployeeById(Long id);
}