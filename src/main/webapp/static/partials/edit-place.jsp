<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!-- PLACE -->

<div ng-controller="placeEditController">
    <div class="form-group">

        <label><span class="glyphicon fui-location"></span> Место проведения курса</label> <select
            class="form-control select-primary" data-toggle="select">
            <option>- выбрать место -</option>
            <option><strong>Кинотеатр "Прощай отрочество"</strong>; Московский 74, ауд. 302</a>
            </option>
            <option>Онлайн</option>
        </select>

    </div>

    <div class="form-group">
        <input id="placeAutoComplete" type="text" class="form-control" 
               ng-model="location" ng-autocomplete
               options="placeSearchOptions" details="locationdetails" placeholder="новый адрес">

            {{location}}<br>
            {{locationdetails.formatted_address}}<br>
            {{locationdetails.geometry.location}}<br>

    </div>
</div>

<!-- end of PLACE -->