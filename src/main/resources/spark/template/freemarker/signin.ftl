<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="60">
  <title>Sign-In</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Sign-In</h1>

  <!-- Provide a navigation bar -->
 <div class="navigation">
 <#if !currentUser??>
     <a href="/">my home</a>
 </#if>
 </div>

  <div class="body">
    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

<form action="./signin" method="POST" id="formUser">
<label for="username">Username:</label><br>
  <input type="text" id="username" name="username"><br>
</form>

<button type="submit" form="formUser">Submit</button>
  </div>

</div>
</body>

</html>
