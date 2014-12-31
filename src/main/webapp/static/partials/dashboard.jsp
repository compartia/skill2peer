<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>
    <a href="/course/create" class="btn btn-default">создать курс</a>
</div>

 
<ul class="nav nav-tabs">
  <li ng-class="tabClass(1)"><a href="#/home/1" ng-click="setSelectedTab(1)">Опубликованные</a></li>
  <li ng-class="tabClass(2)"><a href="#/home/2" ng-click="setSelectedTab(2)">Черновики</a></li>
</ul>
 
<ng-switch on="selectedTab">
  <div ng-switch-when="1">
      <ng-include src="'/static/partials/list-published.jsp'" ></ng-include>
  </div>
  <div ng-switch-when="2">
      <ng-include src="'/static/partials/list-drafts.jsp'"></ng-include>
  </div>
</ng-switch>
 
    

    
 