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
        
        // ���ʵ�¼ҳ��֮ǰ�����ʵ�ҳ�棬��ͨ�����ֵ��ת��֮ǰ��ҳ��
        String returnUri = request.getParameter("return_uri");
        RequestDispatcher rd = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("�Ҳ������� ");
        }
        // ����URL                    
        String url = "jdbc:mysql://localhost:3306/myproject";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        //PreparedStatement s = null;
        
        try {                                                    
            conn = (Connection) DriverManager.getConnection(url, "root", "123456");
            stmt = (Statement) conn.createStatement();
            // SQL���
            String sql = "select * from user where username='" + username + "' and password= '" + password + "'";
            
           
            rs = stmt.executeQuery(sql);// ���ز�ѯ���
    
            if (rs.next()) {
    		    // �����¼���ǿգ�������ƥ����û��������룬��½�ɹ�
    		    //response.sendRedirect("mainform.jsp");
    		    
                /* ��¼�ɹ� */
                // ����¼״̬���浽session������
                request.getSession().setAttribute("flag", "login_success");
                out.print("<script>alert('��¼�ɹ�!');window.location.href='mainform.jsp'</script>");
//                RequestDispatcher dispatcher = request.getRequestDispatcher("mainform.jsp?id="+username);
                //dispatcher.forward(request, response); 
                
                /* �жϵ�¼֮ǰ����һ��ҳ���Ƿ���� */
                if (returnUri != null) {
                    // ��������ת����¼֮ǰ�Ľ���
                    rd = request.getRequestDispatcher(returnUri);
                    rd.forward(request, response);
                } else {
                    // ����������ת����ҳ
                    rd = request.getRequestDispatcher("mainform.jsp");
                    rd.forward(request, response);
                }
    		    
    		} 
            else {
    		    //session.setAttribute("message", "�û��������벻ƥ�䡣");
    		    //response.sendRedirect("error.jsp?a=login");
    		    
                /* ��¼ʧ�� */
                // ����¼״̬�޸�Ϊʧ��
                request.getSession().setAttribute("flag", "login_error");
                request.setAttribute("msg", "�û������������");
                out.print("<script>alert('�û�����������������µ�¼!');window.location.href='login.jsp'</script>");
                // ʧ�ܺ���ת����¼����
//                rd = request.getRequestDispatcher("login.jsp");
//                rd.forward(request, response);
    		    
    		}
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("�쳣");
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
