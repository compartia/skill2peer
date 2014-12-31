<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


 	

         
 <div class="related-courses" ng-cloak >

     <div  ng-repeat="mcourse in myCourses | filter : {status: 'DRAFT'}">
         
         <ng-include src="'/static/partials/course-list-item.jsp'" ></ng-include>
							
     </div>

 </div>
     
    