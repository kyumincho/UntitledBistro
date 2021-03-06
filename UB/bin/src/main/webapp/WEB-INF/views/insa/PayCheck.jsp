<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test</title>

<link rel="stylesheet" type="text/css"
   href="https://cdn.datatables.net/v/dt/dt-1.10.20/rr-1.2.6/sc-2.0.1/sl-1.3.1/datatables.min.css" />

<script type="text/javascript"
   src="https://cdn.datatables.net/v/dt/dt-1.10.20/rr-1.2.6/sc-2.0.1/sl-1.3.1/datatables.min.js"></script>




<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   <table id="WorkList"
      class="table table-striped table-bordered table-hover">
      <thead>
         <tr>
            <th>이름</th>
            <th>시급</th>
            <th>월급</th>
            <th>근무시간</th>
            <th>근무일수</th>
            <th>급여</th>
         </tr>

      </thead>
      <tbody>
      
         <c:forEach var="b" items="${PayCheck}">

            <tr>
               <td>${b.empregister_name}</td>
               <td>${b.empregister_paytime }000원</td>
               <td>${b.empregister_payday }000원</td>
               <td>${b.salary_workingtime }시간</td>
               <td>${b.salary_workingdate }일</td>
               <td>${b.salary_pay }000원</td>
            </tr>
         </c:forEach>
         
      </tbody>
         
   </table>
   <script type="text/javascript">
      $('#WorkList').DataTable({ // 페이징 처리, 검색, show entries
         pageLength : 10,
         bPaginate : true,
         bLengthChange : true,
         lengthMenu : [ [ 10, 20, 30, -1 ], [ 10, 20, 30, "All" ] ],
         bAutoWidth : false,
         processing : true,
         ordering : true,
         serverSide : false,
         searching : true,
         bStateSave : true,
         "iDisplayLength" : 10,
         "language" : {
            search : "Search : "
         },
      });
   </script>

</body>
</html>