<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.ArrayList,java.util.Iterator,cn.ac.sklois.imm.mappings.*,java.sql.*,cn.ac.sklois.imm.admin.*"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>




<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
    String userid=request.getParameter("userid");
    String issueDate = request.getParameter("sdate");
%>

<base href="<%=basePath%>">

<meta http-equiv="content-type" content="text/html; charset=UTF-8">


<script type="text/javascript" src="extjs3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="extjs3/ext-all-debug.js"></script>
<script type="text/javascript" src="extjs3/examples/ux/TableGrid.js"></script>
<!-- <link rel="stylesheet" type="text/css"
	href="extjs3/resources/css/ext-all.css" /> -->
<!-- <link href="tablecloth/tablecloth.css" rel="stylesheet" type="text/css"> -->	
<!-- <style type=text/css>
.x-grid3-row {
　　border-color:#ffaaee;//改变grid边框颜色
　　border-top-color:#fff;
　　}
</style> -->
<style type=text/css>


body {
    margin:auto;
    font-family: 'trebuchet MS', 'Lucida sans', Arial;
    font-size: 14px;
    color: #444;
}

table {
    *border-collapse: collapse;
    border-spacing:0; 
     bordercolor:#dce9f9;
    
}
td {
overflow: hidden;
white-space: nowrap;
text-overflow: ellipsis;
}

.bordered {
    border: solid #dce9f9 1px;
    -moz-border-radius: 6px;
    -webkit-border-radius: 6px;
    border-radius: 6px;
    -webkit-box-shadow: 0 1px 1px #ccc; 
    -moz-box-shadow: 0 1px 1px #ccc; 
    box-shadow: 0 1px 1px #ccc;         
}

.bordered tr:hover {
    background: #fbf8e9;
    -o-transition: all 0.1s ease-in-out;
    -webkit-transition: all 0.1s ease-in-out;
    -moz-transition: all 0.1s ease-in-out;
    -ms-transition: all 0.1s ease-in-out;
    transition: all 0.1s ease-in-out;     
}    
    
.bordered td, .bordered th {
    border-left: 1px solid #dce9f9;
    border-top: 1px solid #dce9f9;
    padding: 8px;
    text-align: left;    
}

.bordered th {
    background-color: #dce9f9;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));
    background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:    -moz-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:     -ms-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:      -o-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:         linear-gradient(top, #ebf3fc, #dce9f9);
    -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset; 
    -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;  
    box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;        
    border-top: none;
    text-shadow: 0 1px 0 rgba(255,255,255,.5); 
}

</style>



<!-- <script language="JavaScript">
 var thisGrid,grid;
	  Ext.onReady(function(){
      // create the grid
      grid = new Ext.ux.grid.TableGrid("tbHistory", {
      title : '远程证明日志',
			trackMouseOver : true,
			loadMask : true,
			header : true,
			enableHdMenu:false,
			collapsible: true,
            stripeRows: true,
            viewConfig : {
				forceFit : true,
				enableRowBody : true
			}
    
        }
        );
        
});

var thisGrid,grid;
       Ext.onReady(function(){
       grid = new Ext.ux.grid.TableGrid("tbWhiteListDetail", {
        	title : '白名单信息',
			trackMouseOver : true,
			loadMask : true,
			header : true,
			enableHdMenu:false,
			collapsible: true,
            stripeRows: true,
            viewConfig : {
				forceFit : true,
				enableRowBody : true
			}
        });
        
});  

 </script> -->

</head>
<body style="font-size:12px;margin-left:150px;" marginheight="0" marginwidth="0" onload="color()" >

		<div  style="overflow-x: auto; overflow-y: auto;width:1350px;margin-right:90px;">
							<form name="a123" id="abc" method=post target='_parent' onsubmit="do_action()">
							 <div style="float:left;widht:40%;">	
								<table class="bordered" id="tb"  width=1200px; style="table-layout: fixed;"
								title="对比日志">
                                    <thead>
                                    <tr style="font-size:16px;">
                                         <th style="text-align:center;font-weight:bold;"width="5%"></th>
                                         <th style="text-align:center;font-weight:bold;"width="15%"></th>
                                         <th style="text-align:center;font-weight:bold;"width="40%"></th>
                                         <th style="text-align:center;font-weight:bold;"width="40%"></th>
                                    </tr>
                                      <tr style="font-size:16px;"   >
                              
                                         <th style="text-align:center;font-weight:bold;"colspan = "4">对比日志</th>
                                    </tr>   
                                    </thead>	
									<tbody id="totable">
										<tr>
										    <td style='background-color:rgb(254, 249, 226);'>序号</td>
											<td style='background-color:rgb(254, 249, 226);'>文件名</td>
											<td style='background-color:rgb(254, 249, 226);'>远程日志度量值</td>
											<td style='background-color:rgb(254, 249, 226);'>白名单度量值</td>
										</tr>
									<%	
									    response.setContentType("text/html");
										request.setCharacterEncoding("UTF-8");
										String driver = "com.mysql.jdbc.Driver";
									    String url1 = "jdbc:mysql://localhost:3306/immdb_new";
									    String sql="";
									    Connection con = null;
									       try {
									        Class.forName(driver);
									        try {
									         con = DriverManager.getConnection(url1,"root","tcwg");
									         Statement stmt=con.createStatement();
									         Statement stmt1=con.createStatement();
									         Statement stmt2=con.createStatement();
									         Statement stmt3=con.createStatement();
									         int i=0;
								             sql = "SELECT * FROM digest_table where userid='"+userid+"' and issueDate='"+issueDate+"'";
									         ResultSet res1 = stmt1.executeQuery(sql);
									         
									         while(res1.next()){
									            i++; 
												out.println("<tr style='background-color:rgb(254, 249, 226);'align='center' height='20'>");
												out.println("<td>" + i + "</td>");
												out.println("<td>" + res1.getString("SoftwareName") + "</td>");
												out.println("<td>" + res1.getString("digest") + "</td>");
												ResultSet res = stmt.executeQuery("SELECT * FROM whitelist_content where userid='"+userid+"' and Process_Name='"+res1.getString("SoftwareName")+"'");
												
												ResultSet res2 = stmt2.executeQuery("SELECT Hash_Value FROM whitelist_content where Process_Name='"+res1.getString("SoftwareName")+"'");
											//	ResultSet res3 = stmt3.executeQuery("SELECT Process_Name FROM whitelist_content where Process_Name='"+res1.getString("SoftwareName")+"'");
											    res.last();
											    int length = res.getRow();
											    if(length == 0){
												    out.println("<td></td>");
												}else if(length != 0)
												{
												    res.beforeFirst();
													while(res.next()){
													    if(!(res.getString("Hash_Value").equals(res1.getString("digest"))))
													    {
													          continue;			    
	                                                         
	                                                    }
	                                                    else{
	                                                          out.println("<td>" + res.getString("Hash_Value") + "</td>");
	                                                          break;
	                                                    } 
	                                                    
													  
													}
													
												
												}
												res.beforeFirst();
												res.next();
												 if((length != 0)&&!(res.getString("Hash_Value").equals(res1.getString("digest"))))
													     out.println("<td>" + res.getString("Hash_Value") + "</td>");		    
	                                                   
												out.println("</tr>"); 
									
											}
											
									         stmt.close();
									         stmt1.close();  
									         con.close();  
									        } catch (SQLException e) {
									         e.printStackTrace();
									        }
									       } catch (ClassNotFoundException e) {
									        e.printStackTrace();
									       }
									%>
									</tbody>	
								</table>
							</div>
				<%-- 			<div style="float:right;widht:40%;">
							<table class="bordered" id="tbWhiteListDetail"  width=600px;  style="table-layout: fixed;"
								title="白名单信息">
                                  <thead>
                                   <tr style="font-size:16px;height:0px;"   >
                                         <th style="text-align:center;font-weight:bold;"width="25%"></th>
                                         <th style="text-align:center;font-weight:bold;"width="75%"></th>
                                    </tr>
                                      <tr style="font-size:16px;"   >
                              
                                         <th style="text-align:center;font-weight:bold;"colspan = "2">白名单信息</th>
                                    </tr> 
                                   
                                    </thead>
								<tbody>
								   <tr style="background-color: rgb(254, 249, 226);">
									<td >文件名</td>
									<td >度量值</td>
								  </tr>
									<%	
									/*     response.setContentType("text/html");
										request.setCharacterEncoding("UTF-8");
										String driver = "com.mysql.jdbc.Driver";
									    String url1 = "jdbc:mysql://localhost:3306/immdb_new";
									    String sql="";
									    Connection con = null; */
									       try {
									        Class.forName(driver);
									        try {
									         con = DriverManager.getConnection(url1,"root","tcwg");
									         Statement stmt=con.createStatement();  
								             sql = "SELECT * FROM whitelist_content where userid='"+userid+"'";
									         ResultSet res = stmt.executeQuery(sql);
									         while(res.next()){
											 
												out.println("<tr style='background-color: rgb(254, 249, 226);'align='center' height='20'>");
												out.println("<td>" + res.getString("Process_Name") + "</td>");
												out.println("<td>" + res.getString("Hash_Value") + "</td>");	
												out.println("</tr>");
									
											}
									         stmt.close();  
									         con.close();  
									        } catch (SQLException e) {
									         e.printStackTrace();
									        }
									       } catch (ClassNotFoundException e) {
									        e.printStackTrace();
									       }
									%>

								</tbody>
							</table>
							<div id="showDiv" style="position: absolute; background-color: white; border: 1px solid black;"></div>
						  </div> --%>	
					</form>		
		      </div>
<script type="text/javascript">
function color(){
        var tab = document.getElementById("tb");
     //   var tab2 = document.getElementById("tbHistory");
        for(var i=3 ;i<tab.rows.length;i++)
        {
          
	       //  for(var j=2;j<tab2.rows.length;j++)
	        //  {
	                 if((tab.rows[i].cells[2].innerHTML)!=(tab.rows[i].cells[3].innerHTML))
	                 {
	                    tab.rows[i].cells[2].style.backgroundColor = "#FFB6C1";
	        
	                 }
	               
	       //   } 
      }
 }

 window.onload = color;
</script>	
</body>

</html>