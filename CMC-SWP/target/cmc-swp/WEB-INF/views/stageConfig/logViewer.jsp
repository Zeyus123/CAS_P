<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Log Viewer</title>
  <style>
    /* Notepad-like styling */
    body {
      font-family: "Courier New", monospace;
      margin: 0;
      padding: 0;
      background-color: #e3e3e3;
      background: radial-gradient(at 50% -20%, #908392, #0d060e) fixed;
    }

    header {
      background-color: transparent;
      color: #fff;
      padding: 10px 0;
      text-align: center;
      display: flex;
      align-items: center;
      justify-content: space-between;
      flex-wrap: wrap;
      width: 94%;
          box-sizing: border-box;
      left: 3%;
      position: relative;
    }

    textarea {
      width: 94%;
      height: 500px;
      padding: 10px;
      font-size: 14px;
      border: 1px solid #ccc;
      background-color: #fff;
      resize: none;
      display: block;
      margin: 0 auto;

    }

    button {
      padding: 10px;
      background: radial-gradient(at 50% -20%, #0d060e, #908392) fixed;
      color: #fff;
      border: none;
      cursor: pointer;
      border-radius: 4px;
    }

    /* Spinner styling */
    .box {
      display: inline-block;
      border-radius: 3px;
      font-size: 30px;
      padding: 1em;
      position: relative;
      margin-bottom: 0.25em;
      vertical-align: top;
      transition: 0.3s color, 0.3s border, 0.3s transform, 0.3s opacity;
    }

    .loaderDiv {
      position: fixed;
      z-index: 999;
      width: 100%;
      height: 100vh;
      display: none;
      align-items: center;
      justify-content: center;
      background: #000000cf;
    }

    .button {
      display: inline-block;
      background: transparent;
      border: 2px solid currentcolor;
      color: white;
      padding: 0.5em 1em;
      border-radius: 5px;
      font-size: calc(16px + 2vw);
    }

    [class*=loader-] {
      display: inline-block;
      width: 1em;
      height: 1em;
      color: inherit;
      vertical-align: middle;
      pointer-events: none;
    }

    .loader-39 {
      position: relative;
      width: 0.15em;
      height: 0.15em;
      background-color: currentcolor;
      border-radius: 100%;
      -webkit-animation: loader-39-1 30s infinite linear;
      animation: loader-39-1 30s infinite linear;
      color: #fff;
    }

    .loader-39:before,
    .loader-39:after {
      content: "";
      border-radius: 100%;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
    }

    .loader-39:before {
      width: 1.3em;
      height: 2em;
      -webkit-animation: loader-39-2 0.8s linear infinite;
      animation: loader-39-2 0.8s linear infinite;
    }

    .loader-39:after {
      width: 2em;
      height: 1.3em;
      -webkit-animation: loader-39-2 1.2s linear infinite;
      animation: loader-39-2 1.2s linear infinite;
    }

    @-webkit-keyframes loader-39-1 {
      0% {
        transform: rotate(0deg);
      }

      100% {
        transform: rotate(360deg);
      }
    }

    @keyframes loader-39-1 {
      0% {
        transform: rotate(0deg);
      }

      100% {
        transform: rotate(360deg);
      }
    }

    @-webkit-keyframes loader-39-2 {
      0% {
        box-shadow: 0.04em -0.04em 0 0.02em currentcolor;
      }

      25% {
        box-shadow: 0.04em 0.04em 0 0.02em currentcolor;
      }

      50% {
        box-shadow: -0.04em 0.04em 0 0.02em currentcolor;
      }

      75% {
        box-shadow: -0.04em -0.04em 0 0.02em currentcolor;
      }

      100% {
        box-shadow: 0.04em -0.04em 0 0.02em currentcolor;
      }
    }

    @keyframes loader-39-2 {
      0% {
        box-shadow: 0.04em -0.04em 0 0.02em currentcolor;
      }

      25% {
        box-shadow: 0.04em 0.04em 0 0.02em currentcolor;
      }

      50% {
        box-shadow: -0.04em 0.04em 0 0.02em currentcolor;
      }

      75% {
        box-shadow: -0.04em -0.04em 0 0.02em currentcolor;
      }

      100% {
        box-shadow: 0.04em -0.04em 0 0.02em currentcolor;
      }
    }



    .gth {
      position: relative;
      padding: 5px 10px;
      background: #fff;
      color: #007acc;
      text-decoration: none;
      font-weight: 600;
      border-radius: 4px;
    }

    /* make textarea scroller id = logContent make it beautifull */
    #logContent::-webkit-scrollbar {
      width: 5px;
    }

    #logContent::-webkit-scrollbar-track {
      box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    }

    #logContent::-webkit-scrollbar-thumb {
      background: radial-gradient(at 50% -20%, #908392, #0d060e);
      border-radius: 15px;
      outline: 1px solid rgb(88, 103, 117);
    }

    



  </style>
</head>

<body>

  <div class="loaderDiv">
    <div class="box">
      <div class="loader-39"></div>
    </div>
  </div>


  <header class="">
    <h2 style="margin:0;">${appLogName} Log Viewer</h2>
    <div style="text-align: center; padding: 10px;">
      <button onclick="fetchLog()">Fetch Log</button>
      <button
        onclick="document.getElementById('logContent').value = 'click on Fetch Log button to see the log';">Clear Console</button>
      <!-- <button onclick="document.getElementById('logContent').select(); document.execCommand('copy');">Copy Log</button> -->
      <button onclick="hardClearLog()">Hard Clear Log</button>
      <button onclick="document.getElementById('downloadForm').submit();">Download Log</button>
      <button onclick="fetchCatLog()">Fetch Catlina Log</button>
      <button onclick="document.getElementById('downloadCatLogForm').submit();">Download Catlina Log</button>
    </div>
    <!-- Go To Home -->
    <a href="${contextPath}" class="gth">Go To Home</a>
  </header>

  <div id="spinner" class="spinner"></div>
  <textarea id="logContent" readonly style="font-family: 'Courier New', monospace; font-size: 14px; border-radius: 4px; margin: 0 auto; display: block; width: 94%;     box-sizing: border-box;height: calc(100vh - 130px); padding: 10px; border: 1px solid #ccc; background-color: #fff; resize: none;">
      click on "Fetch Log" button to see the log
  </textarea>

  <form id="downloadForm" action="${contextPath}/log/downloadLog" method="GET"></form>
 

  <form id="downloadCatLogForm" action="${contextPath}/log/download-cat-log" method="GET"></form>

  <script>

    function showSpinner() {
      // .loaderDiv = display: flex
      document.querySelector('.loaderDiv').style.display = 'flex';
    }

    function hideSpinner() {
      // .loaderDiv = display: none
      document.querySelector('.loaderDiv').style.display = 'none';
    }

    function fetchLog() {
      // Show spinner
      showSpinner();

      fetch('${contextPath}/log/fetchLog')
        .then(response => response.json())
        .then(data => {
          document.getElementById('logContent').value = data.join('\n');
          document.getElementById('logContent').scrollTop = document.getElementById('logContent').scrollHeight;
          // Hide spinner
          hideSpinner();
        })
        .catch(error => {
          console.error('Error fetching log:', error);
          // Hide spinner
          hideSpinner();
        });
    }

    function fetchCatLog() {
      // Show spinner
      showSpinner();
      fetch('${contextPath}/log/fetch-cat-log')
        .then(response => response.json())
        .then(data => {
          document.getElementById('logContent').value = data.join('\n');
          document.getElementById('logContent').scrollTop = document.getElementById('logContent').scrollHeight;
          hideSpinner();
        })
        .catch(error => {
          console.error('Error fetching log:', error);
          // Hide spinner
          hideSpinner();
        });
    }

    async function hardClearLog(){
      debugger;
      // Show spinner
      showSpinner();
      fetch('${contextPath}/log/hard-clear-logs')
        .then(response => response.json())
        .then(data => {
          document.getElementById('logContent').value = data.join('\n');
          document.getElementById('logContent').scrollTop = document.getElementById('logContent').scrollHeight;
          hideSpinner();
        })
        .catch(error => {
          console.error('Error fetching log:', error);
          // Hide spinner
          hideSpinner();
        });
    }



  </script>
</body>

</html>