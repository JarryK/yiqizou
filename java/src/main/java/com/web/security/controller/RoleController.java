package com.web.security.controller;

import com.web.base.Page;
import com.web.base.RestResult;
import com.web.security.jwt.JwtTokenUtils;
import com.web.security.mapper.RoleMapper;
import com.web.security.model.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/yqz/role")
@Api(tags = "Core-权限：角色接口")
public class RoleController {
	
	private final RoleMapper roleMapper;
	
	private final JwtTokenUtils tokenUtils;
	
	@ApiOperation("admin:添加角色")
	@PreAuthorize("hasAnyRole('admin')")
	@GetMapping("/add")
	public RestResult<Object> addRole(String roleName, HttpServletRequest request){
		Role role = new Role();
		role.setName(roleName);
		if(roleMapper.select(role).size() > 0) {
			return RestResult.error("已存在的角色");
		}
		String username = tokenUtils.getTokenUserName(request);
		role.setStatus(true);
		role.setCreateBy(username);
		roleMapper.insert(role);
		return RestResult.success(role);
	}
	
	@ApiOperation("admin:删除角色")
	@PreAuthorize("hasAnyRole('admin')")
	@GetMapping("/delete")
	public RestResult<Object> deleteRole(Long id){
		Role role = new Role();
		role.setId(id);
		roleMapper.deleteByPrimaryKey(role);
		return RestResult.success();
	}
	
	@ApiOperation("admin:修改角色")
	@PreAuthorize("hasAnyRole('admin')")
	@PostMapping("/update")
	public RestResult<Object> updateRole(@RequestBody Role role){
		roleMapper.updateByPrimaryKeySelective(role);
		return RestResult.success(role);
	}
	
	@ApiOperation("admin:查询所有角色")
	@PreAuthorize("hasAnyRole('admin')")
	@GetMapping("/queryAllRole")
	public RestResult<Object> queryAllRole(){
		return RestResult.success(roleMapper.selectAll());
	}
	
	@ApiOperation("admin:分页查询角色")
	@PreAuthorize("hasAnyRole('admin')")
	@PostMapping("/queryRole")
	public RestResult<Object> queryRole(@RequestBody Page<Role> qo){
		Role queryObj = qo.getQueryObj();
		Example example = new Example(Role.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", queryObj.getStatus());
		criteria.andLike("name", "%" + queryObj.getName() + "%");
		return RestResult.success(qo.selectByExample(roleMapper, example));
	}
	
}
