package com.zr.myproject.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // 访问登录页面之前所访问的页面，可通过这个值跳转至之前的页面
        String returnUri = request.getParameter("return_uri");
        RequestDispatcher rd = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("找不到驱动 ");
        }
        // 连接URL                    
        String url = "jdbc:mysql://localhost:3306/myproject";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        //PreparedStatement s = null;
        
        try {                                                    
            conn = (Connection) DriverManager.getConnection(url, "root", "123456");
            stmt = (Statement) conn.createStatement();
            // SQL语句
            String sql = "select * from user where username='" + username + "' and password= '" + password + "'";
            
           
            rs = stmt.executeQuery(sql);// 返回查询结果
    
            if (rs.next()) {
    		    // 如果记录集非空，表明有匹配的用户名和密码，登陆成功
    		    //response.sendRedirect("mainform.jsp");
    		    
                /* 登录成功 */
                // 将登录状态保存到session对象中
                request.getSession().setAttribute("flag", "login_success");
                out.print("<script>alert('登录成功!');window.location.href='mainform.jsp'</script>");
//                RequestDispatcher dispatcher = request.getRequestDispatcher("mainform.jsp?id="+username);
                //dispatcher.forward(request, response); 
                
                /* 判断登录之前的上一个页面是否存在 */
                if (returnUri != null) {
                    // 存在则跳转到登录之前的界面
                    rd = request.getRequestDispatcher(returnUri);
                    rd.forward(request, response);
                } else {
                    // 不存在则跳转到首页
                    rd = request.getRequestDispatcher("mainform.jsp");
                    rd.forward(request, response);
                }
    		    
    		} 
            else {
    		    //session.setAttribute("message", "用户名或密码不匹配。");
    		    //response.sendRedirect("error.jsp?a=login");
    		    
                /* 登录失败 */
                // 将登录状态修改为失败
                request.getSession().setAttribute("flag", "login_error");
                request.setAttribute("msg", "用户名或密码错误");
                out.print("<script>alert('用户名或密码错误，请重新登录!');window.location.href='login.jsp'</script>");
                // 失败后跳转到登录界面
//                rd = request.getRequestDispatcher("login.jsp");
//                rd.forward(request, response);
    		    
    		}
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("异常");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
