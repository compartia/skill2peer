<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>
    <a href="/course/create" class="btn btn-default">создать курс</a>
</div>

<tabset>
    <tab heading="Опубликованные">
    	<ng-include src="'/static/partials/list-published.jsp'" ></ng-include>
	</tab>
    <tab heading="Черновики">
        <ng-include src="'/static/partials/list-drafts.jsp'"></ng-include>
    </tab>
</tabset>
    
    

    
 