<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>



<div ng-show="uploader.isHTML5">
	<!-- 3. nv-file-over uploader="link" over-class="className" -->
 

	<div nv-file-drop="" uploader="uploader">
		<div nv-file-over="" uploader="uploader" over-class="file-over-class" class="well my-drop-zone">Перетащите СЮДА изображение</div>
	</div>


	<div class="progress" style="">
		<div class="progress-bar" role="progressbar" ng-style="{ 'width': uploader.progress + '%' }"></div>
	</div>

</div>

<div class="row">

	<div class="col-md-3"></div>

	<div class="col-md-9" style="margin-bottom: 40px">

		<h3>Upload queue</h3>
		<p>Queue length: {{ uploader.queue.length }}</p>

		<table class="table">

			<tbody>
				<tr ng-repeat="item in uploader.queue">
					<td><strong>{{ item.file.name }}</strong></td>
					<td ng-show="uploader.isHTML5" nowrap>{{ item.file.size/1024/1024|number:2 }} MB</td>
					<td ng-show="uploader.isHTML5">
						<div class="progress" style="margin-bottom: 0;">
							<div class="progress-bar" role="progressbar" ng-style="{ 'width': item.progress + '%' }"></div>
						</div>
					</td>
					<td class="text-center"><span ng-show="item.isSuccess"><i class="glyphicon glyphicon-ok"></i></span> <span
						ng-show="item.isCancel"><i class="glyphicon glyphicon-ban-circle"></i></span> <span ng-show="item.isError"><i
							class="glyphicon glyphicon-remove"></i></span></td>
					<td nowrap>
						<button type="button" class="btn btn-success btn-xs" ng-click="item.upload()"
							ng-disabled="item.isReady || item.isUploading || item.isSuccess">
							<span class="glyphicon glyphicon-upload"></span> Upload
						</button>
						<button type="button" class="btn btn-warning btn-xs" ng-click="item.cancel()" ng-disabled="!item.isUploading">
							<span class="glyphicon glyphicon-ban-circle"></span> Cancel
						</button>
						<button type="button" class="btn btn-danger btn-xs" ng-click="item.remove()">
							<span class="glyphicon glyphicon-trash"></span> Remove
						</button>
					</td>
				</tr>
			</tbody>
		</table>

		<div>
			<div>Queue progress:</div>
			<button type="button" class="btn btn-success btn-s" ng-click="uploader.uploadAll()"
				ng-disabled="!uploader.getNotUploadedItems().length">
				<span class="glyphicon glyphicon-upload"></span> Upload all
			</button>
			<button type="button" class="btn btn-warning btn-s" ng-click="uploader.cancelAll()"
				ng-disabled="!uploader.isUploading">
				<span class="glyphicon glyphicon-ban-circle"></span> Cancel all
			</button>
			<button type="button" class="btn btn-danger btn-s" ng-click="uploader.clearQueue()"
				ng-disabled="!uploader.queue.length">
				<span class="glyphicon glyphicon-trash"></span> Remove all
			</button>
		</div>

	</div>

</div>




