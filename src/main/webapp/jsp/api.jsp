<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="api_defined" type="java.lang.boolean"--%>
<c:if test="${empty api_defined}">
    <c:url value="/" var="root_url" scope="request"/>    
    <c:url value="/users" var="user_url" scope="request"/>    
    <script type="text/javascript">
        window.rootApiUrl = '${root_url}';
        window.userApiUrl = '${user_url}';
    </script>
    <c:set var="api_defined" value="true" scope="request"/>
</c:if>