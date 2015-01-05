<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!-- PLACE -->

<div ng-controller="placeEditController">
    <div class="form-group">

        <label><span class="glyphicon fui-location"></span> Место проведения курса</label>
            <select ng-model="lesson.location.id" class="form-control select-primary" data-toggle="select" ng-change="onPlaceChanged()">
            <option value="-1">- новый адрес -</option>
            <option value="-2">* Онлайн *</option>
            </option>
    
            <option ng-repeat="loc in availableLocations" value="{{loc.id}}">{{loc.address}}</option>
           
        </select>
        <span class="help-inline small">{{lesson.location.address}}</span>
    </div>

    <div class="form-group" ng-show="lesson.location.id==-1">
        <input id="placeAutoComplete" type="text" class="form-control" 
               ng-model="location" ng-autocomplete
               options="placeSearchOptions" details="locationdetails" placeholder="новый адрес">
        
        <span class="help-inline small">
        <img ng-show="locationdetails.icon!=null" style="width:1.5em; height: auto" src="{{locationdetails.icon}}"/> {{lesson.location.address}}</span>
        
       
        <br>{{lesson.location.geometry}} 
        <br>formatted_address:{{locationdetails.formatted_address}}<br>
         
<!-- 
            location:{{location}}<br>
            formatted_address:{{locationdetails.formatted_address}}<br>
            {{locationdetails.geometry.location}}<br> -->

        <textarea class="form-control" rows="2" ng-model="lesson.location.description"
						placeholder="Комментарий к адресу. Код домофона? Этаж? Номер аудитории? Пароль на охране? Любимые конфеты собаки консьержа?"></textarea>
    </div>
</div>

<!-- end of PLACE -->