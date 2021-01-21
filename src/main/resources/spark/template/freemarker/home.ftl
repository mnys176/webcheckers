<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="5">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />
<#if !currentUser??>
<p>
Current amount of players online: ${playersOnline}
</p>
<br/>
<#else>
    <p><u>List of Players</u></p>
    <#list users as obj>
           <#if obj.getName()!=currentUser.name>
            <form action="/game" method="get" id="formChallenge">
                <input type="submit" value="${obj.getName()}" name="opponent">
                <button type="submit" form="formChallenge" value="${obj.getName()}" name="opponent"> Challenge Player </button>ELO: ${obj.getELO()}
            </form>
        </#if>
    </#list>
    <p><u>Active games</u></p>
    <#list active as key,game>
        <form action="/game" method="get" id="formActive">
            <input type="submit" value="${game.getOtherPlayer(currentUser).name}" name="opponent">
            <button type="submit" form="formActive" value="${game.getOtherPlayer(currentUser).name}" name="opponent"> Go To Game </button>
        </form>
    </#list>
    <p><u>Recent Games</u></p>
    <#list replays as key,replay>
        <form action="/replay/game" method="get" id="formReplay">
            <input type="hidden" value="${key}" name="gameID">
            ${replay.getRedPlayer().getName()} vs. ${replay.getWhitePlayer().getName()}, Replay #${replay.getLastCharID()}  <input type="submit" value="Go to replay" name="opponent">
            Combined ELO: ${replay.SumELO()}
        </form>

    </#list>
    <h3>Personal stats: </h3>
    <p>Win rate: ${currentUser.winRate()}%</p>
    <p>Most games played against: ${friend}</p>
</#if>

  </div>

</div>
</body>

</html>
