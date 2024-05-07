<%@taglib  uri="http://www.springframework.org/tags/form"   prefix="form"%>
<%@taglib   uri="http://java.sun.com/jsp/jstl/core"     prefix="c" %>
<br>  <br>
<c:if   test="${message ne  null }">
     <c:out  value="${message}"/>
</c:if>
<hr>
<br>
<form:form  action="loginCustomer"   method="post" modelAttribute="loginBean">
  <table>
    <tr>
         <td> PhoneNo </td>
         <td> <form:input path="phoneNumber"/> </td>
    </tr>
    <tr>
         <td> Password </td>
         <td> <form:input path="password"/> </td>
    </tr>
    <tr>
       <td  colspan=2   align="center">
             <input  type="submit"   value="Signin">
       </td>
  </table>

</form:form>


