<%@ page trimDirectiveWhitespaces="true"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title>Lection</title>
<meta name="keywords" content="lection example html css js" />
<meta name="description" content="Lection example page for classwork" />
<%@ include file="/jsp/api.jsp"%>
</head>
<body>
    <p>Initial page</p>
    <br>
    <ul>
        <li>element 1</li>
        <li>element 2</li>
        <li>element 3</li>
    </ul>
    <br>
    <select>
        <option>option 1</option>
        <option>option 2</option>
        <option>option 3</option>
    </select>
    <br>
    <img alt="loading" src="${root_url}images/loading.gif" />
    <br>
    <a href="http://www.google.com">Google</a>

    <table border="1px">
        <thead>
            <tr>
                <th>num1</th>
                <th>num2</th>
                <th>num3</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>1</td>
                <td>2</td>
                <td>3</td>
            </tr>
            <tr>
                <td>4</td>
                <td>5</td>
                <td>6</td>
            </tr>
        </tbody>
    </table>
    <br>
    <br>
    <form action="" method="POST">
        <input name="myname" type="text" placeholder="your name"/> 
    <br>
        <input name="secret" type="text" value="42"/> 
    <br>
        <textarea name = "textarea" rows="5" cols="5"></textarea>
    <br>
        <input type="submit" value="knopka"/>
    <br>
        <button>Another knopka</button>
    </form>
    <br>
    <br>
    <br>
    <form:form action="${root_url}lection/model" modelAttribute="user">
        <form:input path="id" disable="disabled"/>
        <form:input path="login"/>
        <form:input path="email"/>
        <form:button> Clickme</form:button>
    </form:form>

</body>
</html>