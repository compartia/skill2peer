<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


 	
  
         
 <div class="related-courses" ng-cloak >

     <div class="related-course-block" ng-repeat="mcourse in myCourses | filter : {status: 'DRAFT'}">
         <div class="img-wrap">
             <img src="{{mcourse.imageUrl}}"/>
         </div>

         <div class="text-wrap {{mcourse.status=='PUBLISHED'?'':'draft-course'}} {{mcourse.past?'past-course':''}}">
             <div class="related-course-description">
                 
                 <time class="related-course-date">
                      {{mcourse.scheduleInfo.dates}}<br>
                      {{mcourse.totalDurationAsString}}
                     </time>
                 <h3 class="related-course-title">{{mcourse.name}}</h3>
                 

                 <p>{{mcourse.scheduleInfo.nextEvent}}</p>


                 <a ng-show="mcourse.status=='PUBLISHED'" href="/course/info?id={{mcourse.id}}">view</a>
                 <a href="#/edit/{{mcourse.id}}">edit</a>

             </div>
         </div>									
     </div>

 </div>
     
    