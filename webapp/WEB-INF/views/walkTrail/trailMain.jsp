<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>trailMain</title>
<link href="${pageContext.request.contextPath}/assets/css/walkTrail/trailMain.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ovgjjriioc"></script>
</head>
<body>
	<jsp:include page="../global/header.jsp"></jsp:include>
	<div class="contents">
		<h1>산책로 추천</h1>

		<div class="explorer">
			<div class="segment-map-filters">
				<div class="input-group mb-3">
					<input type="text" class="form-control" placeholder="서울 특별시 강동구 천호동" aria-label="Recipient's username" aria-describedby="button-addon2">
					<button class="btn btn-outline-secondary" type="button" id="button-addon2">
						<i class="fa-solid fa-magnifying-glass"></i>
					</button>
				</div>
				
				<!-- Button trigger modal -->
				<button type="button" class="btn btn-secondary modalBtn" data-bs-toggle="modal" data-bs-target="#tagModal"></button>
				<!-- tagModal -->
				<div class="modal fade" id="tagModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h1 class="modal-title fs-5" id="exampleModalLabel">태그 선택</h1>
								<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							</div>
							<div class="modal-body">
								<button class="btn badge btn1">공원 근처</button>
								<button class="btn badge btn1">산 근처</button>
								<button class="btn badge btn1">카페 근처</button>
								<button class="btn badge btn2">넓은 공간</button>
								<button class="btn badge btn2">흙길</button>
								<button class="btn badge btn2">액티비티</button>
								<button class="btn badge btn3 step2 limit">유동인구 많음</button>
								<button class="btn badge btn3 step2 limit">유동인구 적음</button>
								<div class="form-check step2">
									<input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
									<label class="form-check-label" for="flexCheckDefault">태그선택 취소</label>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-light" data-bs-dismiss="modal">취소</button>
								<button type="button" class="btn btn-secondary" id="tagSelected">완료</button>
							</div>
						</div>
					</div>
				</div>
				
				<div class="btn-group" role="group" aria-label="Default button group">
					<button type="button" class="btn btn-outline-dark">이용자순</button>
					<button type="button" class="btn btn-outline-dark">인기순</button>
					<button type="button" class="btn btn-outline-dark">최신순</button>
					<button type="button" class="btn btn-outline-dark">오래된순</button>
				</div>

				<button type="button" class="btn btn-primary">MY</button>
			</div>

			<div class="main-content">
				<div class="segments-sidebar" id="segments-sidebar">
					<ul id="trailList"></ul>
				</div>
				<i class="fa-solid fa-angles-left" id="fa-angles"></i>
				<div class="main-map" id="map"></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

	/* map */
	var map = new naver.maps.Map('map', {
		center: new naver.maps.LatLng(37.5446493, 127.1243433),
		zoom: 16
	});
	let overlay = [];
	
	$(document).ready(function() {
		getNewCoords();
	})
	naver.maps.Event.addListener(map, 'zoom_changed', function() {
		// console.log("zoom_changed()");
		getNewCoords();
    })
    naver.maps.Event.addListener(map, 'dragend', function() {
    	// console.log("dragend()");
    	getNewCoords();
    })
    
    function getNewCoords() {
		// ne 북동 / sw 남서
		let coordsMap = {
			neX : map.getBounds()._ne.x,
			neY : map.getBounds()._ne.y,
			swX : map.getBounds()._sw.x,
			swY : map.getBounds()._sw.y,
		}
		console.log("coordsMap ", coordsMap);
		
		fetchList(coordsMap);
	}

	function fetchList(coordsMap) {
		// console.log("fetchList()");
		// console.log("coordsMap ", coordsMap);
		
		$.ajax({
			url : "${pageContext.request.contextPath}/walkTrail/listMap",
			type : "get",
			data : coordsMap,
			
			dataType : "json",
			success : function(listMap) {
				// console.log("overlay.length ", overlay.length);
				
				for(let i = 0; i < overlay.length; i++) {
					overlay[i].setMap(null);
				}
				overlay.length = 0;
				$("#trailList").empty();
				
				for(let i = 0; i < listMap.trailList.length; i++) {
					listRender(listMap.trailList[i]);
					mapRender(listMap.coordsList[i]);
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		}); 
	}
	
	// trail list
	function listRender(trailVo) {
		// console.log("listRender()");
		
		let str = '';
		str += '<li>';
		str += '	<i class="fa-solid fa-location-dot fa-2x"></i>';
		str += '	<div>';
		str += '		<span class="sideBar-title">' + trailVo.name + '</span><br>';
		str += '		<span>' + trailVo.distance + '</span>';
		str += '		<span>' + trailVo.eta + '</span>';
		str += '	</div>'
		str += '</li>';
		
		$("#trailList").append(str);
	}
	
	// coords list
	function mapRender(coords) {
		// console.log("mapRender()");
		
		let path = [];
		for(let i = 0; i < coords.length; i++) {
			path.push(new naver.maps.LatLng(coords[i].lat, coords[i].lng));
		}
		
		var polyline = new naver.maps.Polyline({
	        path: path,
	        strokeColor: '#fc5200',
	        strokeOpacity: 0.8,
	        strokeWeight: 5,
	        zIndex: 2,
	        clickable: true,
	        map: map
	    });
		
		var marker = new naver.maps.Marker({
	        map: map,
	        position: path[0]
	    });
		overlay.push(polyline);
		overlay.push(marker);
	}
	
	/* Non-list */
 	$("#fa-angles").on("click", function() {
		// console.log("fa-angles click");
		
		if(document.getElementById("map").classList.contains("nonList")) {
			document.getElementById("fa-angles").classList.replace("fa-angles-right", "fa-angles-left");
		} else {
			document.getElementById("fa-angles").classList.replace("fa-angles-left", "fa-angles-right");
		}
		document.getElementById("segments-sidebar").classList.toggle("nonList");
		document.getElementById("map").classList.toggle("nonList");
	});
	
 	/* filter */
 	const filterGroup = document.querySelectorAll(".btn-group button");
 	let filterIndex = 0;
 	
 	filterGroup[filterIndex].classList.add("selected-filter");
 	
 	filterGroup.forEach(function (item, index) {
        item.addEventListener("click", function () {
        	filterGroup[filterIndex].classList.remove("selected-filter");
        	filterIndex = index;
        	filterGroup[filterIndex].classList.add("selected-filter");
        })
    });
	
	/* tag */
	$(".modalBtn")[0].innerHTML = '태그 선택&nbsp;&nbsp;&nbsp<i class="fa-solid fa-chevron-down"></i>';
	
 	const tagGroup = document.querySelectorAll("#tagModal .modal-body button");
 	let tagActive = false;
 	let tagArr = [];
 	
 	tagGroup.forEach(function (item, index) {
        item.addEventListener("click", function () {
        	
        	let $this = $(this);
        	
        	if($this.hasClass("selected-tag")) {
        		if(tagArr.length != 0) {
            		if($this.hasClass("limit")) {
            			tagActive = false;
        			}
            		$this.removeClass("selected-tag");
            		
        			let tagArrIndex = tagArr.indexOf(item);
        			tagArr.splice(tagArrIndex, 1);
        		}
        	} else {
        		if(tagArr.length < 2) {
        			if($this.hasClass("limit")) {
            			if(!tagActive) {
            				tagActive = true;
            				$this.addClass("selected-tag");
            				tagArr.push(item);
            			}
            		} else {
            			$this.addClass("selected-tag");
            			tagArr.push(item);
            		}
        		}
        	}
        })
    });
 	
 	// tag selected
 	$("#tagSelected").on("click", function() {
		// console.log("tagSelected click");
		
		tagList();
		
		$('#tagModal').modal('hide');
	});
 	
 	// tag list
 	let tagList = function() {
 		console.log("tagArr ", tagArr);
 		
 		$(".modalBtn")[0].innerHTML = "";
 		
 		if(tagArr.length != 0) {
 			for(let i = 0; i < tagArr.length; i++) {
				let newTag = tagArr[i].cloneNode(true);
				
				$(".modalBtn")[0].append(newTag);
			}
 		} else {
 			$(".modalBtn")[0].innerHTML = '태그 선택&nbsp;&nbsp;&nbsp<i class="fa-solid fa-chevron-down"></i>';
 		}
 	};
 	
</script>
</html>