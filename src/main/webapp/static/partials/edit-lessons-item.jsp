<%@ page contentType="text/html;charset=UTF-8" language="java"%>
 
        
<a ng-click="selectLesson(ls)" >
    <h4 class="list-group-item-heading">
        <button ng-click="deleteLesson(ls)"><small  class="pull-right glyphicon glyphicon-remove"></small></button>
      {{ls.name}}</h4>


    <p class="list-group-item-text text-info">
        <time>
            <strong>ВТ</strong> {{ls.schedule.dateTime | date:"dd.MM.yyyy HH:mm"}}<span ng-show="ls.schedule.recurrent"
                class="glyphicon glyphicon-repeat"></span>
        </time>
        <address>{{ls.locaion.address}}</address>
    </p>
</a>
        
         