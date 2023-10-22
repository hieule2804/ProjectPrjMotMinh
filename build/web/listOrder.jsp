<%-- Document : Foradmin Created on : Oct 11, 2023, 9:21:37 AM Author : ADMIN --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://fonts.googleapis.com/css2?family=Cairo:wght@200;300;400;600;900&display=swap"
              rel="stylesheet">

        <!-- Css Styles -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
              integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
              crossorigin="anonymous">
        <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
    </head>

    <body>
        <%
if( session.getAttribute("account") == null)
{
response.sendRedirect("login.jsp");
}
        %>
        <!--header-->
        <div class="header container ">
            <div class="firtHeader row" style="    margin-top: -20px;">
                <div class="logoHearder col-md-2">
                    <img style="height: 200px;"
                         src="https://png.pngtree.com/template/20191125/ourlarge/pngtree-book-store-logo-template-sale-learning-logo-designs-vector-image_335046.jpg"
                         alt="">
                </div>
                <div class="rightHeader col-md-10" style="margin-top: 79px;">
                    <div class="seachHeader col-md-8">
                        <!--Form Search-->
                        <form style="width: 700px;" action="admin?action=searchBook" method="post" role="search">
                            <input style="    margin-left: 20px;
                                   width: 500px;
                                   height: 35px;" type="text" name="search" value="" placeholder="Tìm kiếm sản phẩm... "
                                   class="input-group-field st-default-search-input search-text"
                                   autocomplete="off">
                            <select name="type">
                                <option value="searchbook">BOOK</option>
                                <option value="searchaccount">Account</option>

                            </select>

                            <button style="    width: 35px; border: 0px;
                                    height: 33px;" tyle="submit " class="glyphicon glyphicon-search">
                                <i class="fa fa-search"></i>
                            </button>
                        </form>
                    </div>
                    <!-- nut cua dang nhap-->
                    <div class="itemHearder col-md-4" style="      padding-top: 7px;
                         margin-left: 0px;
                         padding-left: 50px;">
                        <%
                 if( session.getAttribute("account") ==null)
                 {
                        %>
                        <div class="taikhoan col-md-6">
                            <div onclick="LoginLogout()" class="login">

                                <a href="#" class="cart-label header-icon">
                                    <div class="cart-info" style="padding-left: 20px;">
                                        <span class="glyphicon glyphicon-user header-icon"></span>
                                    </div>
                                </a>
                                <span class="block">Tài khoản</span>
                            </div>
                            <div class="top-cart-content" id="LoginLogout" style="display: none;     margin-left: -30px;">
                                <ul>

                                    <a href="login.jsp"><i class=""></i> Đăng nhập
                                    </a></li>
                                    <a href="register.jsp"><i class="fa fa-shopping-cart"></i> Đăng ký
                                    </a></li>

                                </ul>
                            </div>
                        </div>
                        <%  
                            }else{
                        %>

                        <!--Tai khoan sau khi dang nhap-->
                        <div class="taikhoan col-md-6">
                            <div onclick="LoginLogout()" class="login">

                                <a href="#" class="cart-label header-icon">
                                    <div class="cart-info" style="padding-left: 20px;">
                                        <span class="glyphicon glyphicon-user header-icon"></span>
                                    </div>
                                </a>
                                <span class="block">${account.username}</span>
                            </div>
                            <div class="top-cart-content" id="LoginLogout" style="display: none;     margin-left: -30px;">
                                <ul>

                                    <a href="login"><i class=""></i> LOG OUT
                                    </a></li>
                                </ul>
                            </div>
                        </div>
                        <%   }
                        %>

                    </div>
                </div>
            </div>
            <!---->

            <!-- list the loai sach -->

            <nav class="navbar navbar-default " style="margin-top: -20px;">
                <div class="btn-group">
                    <button onclick="Menu()" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Book <span class="caret"></span>
                    </button>

                    <ul class="dropdown-menu" id="menu" style="display: none;">
                        <li style="margin-right: 15px;">
                            <form action="admin?action=getAllBook" method="post">
                                <input style="border: 0px;
                                       height: 50px;" type="submit" value="ALL BOOK">
                            </form>
                        </li>

                        <!--sẽ dùng c:foreach trong jsp và sử dụng list thể lại sách để cho vào tên và link-->
                        <c:forEach items="${listcate}" var="cate">
                            <li style="margin-right: 15px;" >
                                <form action="admin?action=searchByCate&id=${cate.id}" method="post">
                                    <input style="border: 0px;
                                           height: 50px;" type="submit" value="${cate.categori}">
                                </form>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <!-- list account -->
                <form style="margin-left: 300px;
                      margin-top: -30px;" action="adminaccount?action=getaccount" method="post">
                    <input type="submit" value="Account">
                </form>

                <!-- don hang -->
                <form style="margin-left: 430px;
                      margin-top: -25px;" action="admin?action=getOrder" method="post">
                    <input type="submit" value="order">
                </form>
            </nav>
        </div>
    </div>

    <!--list Order-->
    <div>
        
        <table border="1" style="width: 400px;
    margin-left: 300px;">
        <thead>
            <tr>
                <th>OrderID</th>
                <th>CustomerName</th>
                <th>Date</th>
                <th>TotalMoney</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${listOrder}" var="order">
                
            <tr>
                <td>${order.id}</td>
                <td>${order.cusname}</td>
                <td>${order.date}</td>
                <td>${order.totaloney}</td>
                <td>
                    <form action="order?action=detail&id=${order.id}" method="post">
                        <input type="submit" value="Detail">
                    </form>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
        <!--list thong ke-->
        <table border="1" style="margin-left: 800px;
    margin-top: -124px;">
           <thead>
               <tr>
                   <th>customers buy the most</th>
                   <th>best - selling</th>
               </tr>
           </thead>
           <tbody>
               <tr>
                   <td>${theMostUser}</td>
                   <td>${bestselling.name}${bestselling.count}</td>
               </tr>
            
           </tbody>
       </table>
    </div>

  
</body>

</html>
<script>
    function Menu() {
        let x = document.getElementById('menu');
        if (x.style.display == 'none') {
            x.style.display = 'block';
        } else {
            x.style.display = 'none';
        }
    }


    function LoginLogout() {
        let x = document.getElementById('LoginLogout');
        if (x.style.display == 'none') {
            x.style.display = 'block';
        } else {
            x.style.display = 'none';
        }
    }
</script>