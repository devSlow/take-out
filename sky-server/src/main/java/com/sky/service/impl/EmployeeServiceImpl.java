package com.sky.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import static org.apache.commons.lang3.StringUtils.getBytes;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    //    密码加盐处理
    public static final String SALT = "slow";

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码加密
        String handerPassword = DigestUtils.md5DigestAsHex((password + SALT).getBytes());
        if (!handerPassword.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }


    /**
     * @param employeeDTO
     * @author Slow
     * @CurrentTime 2024年7月29日12:21:09
     */
    public void insertEmployee(EmployeeDTO employeeDTO) {
        //实现类方便提交前端传的数据，最后给到持久层还是employee
        Employee employee = new Employee();
//        对象属性拷贝
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword(DigestUtils.md5DigestAsHex(("123456" + SALT).getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(employee.getCreateUser());
        employee.setUpdateUser(employee.getUpdateUser());
        employeeMapper.insertEmployee(employee);
    }

    /**
     * @param employeePageQueryDTO
     * @return
     * @author Slow
     * @CurrentTime 2024年7月29日15:36:41
     */
//    在serviceImpl中使用pageHelper实现分页
    public PageResult pageQueryEmployee(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
//        调用DAO的时候，需要返回page对象
        Page<Employee> page = employeeMapper.pageQueryEmployee(employeePageQueryDTO);
        long total = page.getTotal();
        List<Employee> result = page.getResult();
        return new PageResult(total, result);
    }

    /**
     * 启用禁用员工账号
     *
     * @param status
     * @param id
     * @author Slow
     * @CurrentTime 2024年7月29日17:08:02
     */
    public void startOrStop(Integer status, Long id) {
//        update语句修改，使其更有通用性
        Employee employee = new Employee();
        employee.setId(id);
        employee.setStatus(status);
        employeeMapper.update(employee);
    }

    /**
     * 编辑员工信息
     *
     * @param employeeDTO
     * @author Slow
     * @currentTime 2024年7月29日20:01:09
     */
    public void updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }


    public Employee QueryEmployeeById(Long id) {
      Employee employee=  employeeMapper.QueryEmployeeById(id);
      employee.setPassword("*******");
        return employee;
    }
}

