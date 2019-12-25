package com.zr.myproject.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String email = request.getParameter("email");
        PrintWriter out = response.getWriter();
        
        if (username == "")
        {
        	out.print("<script>alert('用户名不能为空!');window.location.href='register.jsp'</script>");
        }
        else if (password == "")
        {
        	out.print("<script>alert('密码不能为空!');window.location.href='register.jsp'</script>");
        }
        else if (email == "")
        {
        	out.print("<script>alert('邮箱不能为空!');window.location.href='register.jsp'</script>");
        }
        else if (password.equals(password2)==false)
        {

        	out.print("<script>alert('两次输入密码不一致，请重新输入!');window.location.href='register.jsp'</script>");
            password = "";
            password2 = "";
        }
        else {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("找不到驱动 ");
            }
            // 连接URL        
            String url = "jdbc:mysql://localhost:3306/myproject";
            Connection conn = null;

            PreparedStatement s = null;
            
            try {                                                    
                conn = (Connection) DriverManager.getConnection(url, "root", "123456");
                
                String sql="insert into user(username,password,email) value(?,?,?)";
                s = (PreparedStatement) conn.prepareStatement(sql);
                s.setString(1,username);
                s.setString(2,password);
                s.setString(3,email);
                s.executeUpdate();
                System.out.println("success");
                out.print("<script>alert('注册成功!');window.location.href='login.jsp'</script>");
                
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("异常");
            }
        }
        

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
