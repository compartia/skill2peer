<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>




<div class="form-group">
	<input name="name" ng-model="course.name" type="text" class="form-control input-lg" placeholder="название курса">
</div>

<!-- SKILLS -->
<div class="form-group">
	<label>Навыки <br> 
		<small>Укажите навыки(skills), которые студенты приобретут, пройдя этот курс.
			Не более пяти.</small></label>
	
	<tags-input ng-model="course.tags"></tags-input>
</div>
<!-- end of SKILLS -->

<div class="form-group">
	<textarea class="form-control" rows="3" placeholder="краткое описание курса" ng-model="course.summary"></textarea>
	<span class="help-inline small">Этот текст будет выводиться в результатах поиска</span>
</div>



