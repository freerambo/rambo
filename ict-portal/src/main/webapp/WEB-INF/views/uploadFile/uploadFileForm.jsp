<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
<title>Upload File Request Page</title>
</head>
<body>
 <h1> Upload Single File  
</h1> 
    <form method="POST" action="${ctx }/upload" enctype="multipart/form-data">
        File to upload: <input type="file" name="file"><br /> 
       <!--  Name: <input type="text" name="name"><br /> <br />  -->
        <input type="submit" value="Upload"> Press here to upload the file! (<font color = "red">Noted: cant exceed 10MB!</font>)
    </form>
     
     
<h1> Upload Multiple File
</h1>
    <form method="POST" action="${ctx }/uploadMultipleFile" enctype="multipart/form-data">
        File1 to upload: <input type="file" name="file" multiple><br /> 
<!--         Name1: <input type="text" name="name"><br /> <br /> 
 -->        File2 to upload: <input type="file" name="file"><br /> 
<!--         Name2: <input type="text" name="name"><br /> <br />
 -->        <input type="submit" value="Upload"> Press here to upload the file!
    </form>

</body>
</html>