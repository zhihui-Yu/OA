# OA
OA(TestVersion)<br><br>
### 项目说明:<br><br>
   &nbsp;&nbsp;&nbsp;&nbsp;实现办公自动化系统的报销单模块，根据登入用户的权限，进入系统后可操作模块也有响应变化。<br>
   &nbsp;&nbsp;&nbsp;&nbsp;实现：JDK1.7+SSM+Tomcat7
### 项目功能:
  1. 员工填写报销单及详细信息。<br>
  2. 提交后由部门经理审核，超过1W，部门经理审核通过也需要总经理复审才能转到财务打款。<br>
  3. 部门经理填写的报销单由总经理审核，通过则转到财务。<br>
  4. 部门经理可以添加员工。<br>
  5. 总经理可以添加部门，员工。<br>

### 使用说明：
   1. 登入：http://localhost:8080/to_login(注意：要将项目根路径修改为/)<br>
   2. 登入账号都多种权限，可以看sql文件，例：总经理：账号：10001 密码：000000<br>
   
### 效果图：
![image](https://github.com/zhihui-Yu/OA/tree/master/image/login.png)
![image](https://github.com/zhihui-Yu/OA/tree/master/image/main.png)
