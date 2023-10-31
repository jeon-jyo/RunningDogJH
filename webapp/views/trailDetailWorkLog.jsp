<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>trailDetailWorkLog</title>
<link href="${pageContext.request.contextPath}/assets/css/trailDetail.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=ovgjjriioc"></script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="contents">
		<h1><i class="fa-regular fa-star"></i>&nbsp;천호공원 한바퀴&nbsp;&nbsp;&nbsp;<i class="fa-solid fa-diagram-project"></i></h1>
		
		<div class="section">
			<div class="location">서울 강동구 올림픽로 702 해공도서관</div>
			<div class="stat">
				<span class="stat-subtext">거리</span>
				<span class="stat-text">1.87km</span>
			</div>
			<div class="stat">
				<span class="stat-subtext">소요시간</span>
				<span class="stat-text">30분</span>
			</div>
			<div class="stat">
				<span class="stat-subtext">이용자</span>
				<span class="stat-text">1k</span>
			</div>
			<div class="stat">
				<span class="stat-subtext">찜</span>
				<span class="stat-text">391</span>
			</div>
			<div class="stat">
				<span class="stat-subtext">후기</span>
				<span class="stat-text">1k</span>
			</div>
			<div class="stat2">이번주 이 산책로를 이용한 메이트수 <strong>200</strong></div>
		</div>
		
		<div class="map-container">
			<div class="map-container-left">
				<div class="main-map" id="map"></div>
				
				<div class="map-info">
					<div class="map-detail-info">
						<div class="tag-btn">
							<button class="btn badge btn1">공원 근처</button>
							<button class="btn badge btn3 step2">유동인구 많음</button>
						</div>
						<div class="check-info">
							<span>주차장 있음</span>
							<span>화장실 있음</span>
							<span>쓰레기통 있음</span>
						</div>
						<div class="user-modify">
							<i class="fa-solid fa-pen"></i>
							<i class="fa-solid fa-trash"></i>
						</div>
					</div>
					
					<div class="map-detail-text">
						저녁에 강아지들이 산책하러 많이 옴
						쓰레기통 두 개나 있어서 좋음
						주변에 주택가라서 조용함
					</div>
					
					<div class="map-detail-chart detail-border">
						<h2><i class="fa-solid fa-chart-simple"></i>&nbsp;&nbsp;&nbsp;산책로 이용 시간대</h2>
						<div class="detail-chart">chart</div>
					</div>
					
					<div class="map-detail-meeting">
						<h2><i class="fa-solid fa-user-group"></i>&nbsp;&nbsp;&nbsp;오늘의 산책모임</h2>
						<div class="detail-meeting">
							<i class="fa-solid fa-chevron-left"></i>
							<div class="meetings">
								<div class="metting">
									<img src="${pageContext.request.contextPath}/assets/images/sarang4.jpg">
									<span>동아리 이름</span>
								</div>
								<div class="metting">
									<img src="${pageContext.request.contextPath}/assets/images/sarang4.jpg">
									<span>동아리 이름</span>
								</div>
								<div class="metting">
									<img src="${pageContext.request.contextPath}/assets/images/sarang4.jpg">
									<span>동아리 이름</span>
								</div>
								<div class="metting">
									<img src="${pageContext.request.contextPath}/assets/images/sarang4.jpg">
									<span>동아리 이름</span>
								</div>
								<div class="metting">
									<img src="${pageContext.request.contextPath}/assets/images/sarang4.jpg">
									<span>동아리 이름</span>
								</div>
							</div>
							<i class="fa-solid fa-chevron-right"></i>
						</div>
					</div>
				</div>
			</div>
			
			<div class="map-container-right">
				<div class="map-sidebar">
					<h2>나의 기록</h2>
					<div class="detail-bar">
						<img src="${pageContext.request.contextPath}/assets/images/sarang2.jpg">
						<div class="detail-text">
							<span>내 닉네임</span>
							<span><i class="fa-solid fa-person-walking"></i>&nbsp;&nbsp;&nbsp;<strong>20</strong></span>
						</div>
					</div>
					<h2>최근 일지</h2>
					<div class="record-bar">
						<div class="detail-text">
							<span>산책 기록이 없습니다.</span>
						</div>
						<div class="detail-text">
							<span>2023/10/02</span>
							<span>30분</span>
							<span>800m</span>
							<i class="fa-solid fa-chevron-right"></i>
						</div>
						<div class="detail-text">
							<span>2023/10/02</span>
							<span>30분</span>
							<span>800m</span>
							<i class="fa-solid fa-chevron-right"></i>
						</div>
					</div>
				</div>
				
				<div class="map-sidebar share-sidebar">
					<h2>공유 메이트</h2>
					<div class="detail-bar">
						<img src="${pageContext.request.contextPath}/assets/images/sarang3.jpg">
						<div class="detail-text">
							<span>닉네임</span>
							<span><i class="fa-solid fa-person-walking"></i>&nbsp;&nbsp;&nbsp;<strong>20</strong></span>
						</div>
					</div>
					<div class="record-bar share-detail">
						<div class="detail-text">
							<span class="textName">작성일</span>
							<span>2023/08/08</span>
						</div>
						<div class="detail-text">
							<span class="textName">마지막 수정일</span>
							<span>2023/09/09</span>
						</div>
					</div>
				</div>
				
				<div class="map-sidebar ranking-sidebar">
					<h2><i class="fa-solid fa-crown"></i>&nbsp;가장 많이 이용한 메이트</h2>
					<div class="ranking-detail-bar">
						<img src="${pageContext.request.contextPath}/assets/images/sarang5.jpg">
						<div class="detail-text">
							<span>닉네임</span>
							<span><i class="fa-solid fa-person-walking"></i>&nbsp;&nbsp;&nbsp;<strong>20</strong></span>
						</div>
					</div>
					<div class="ranking-detail-bar">
						<img src="${pageContext.request.contextPath}/assets/images/sarang5.jpg">
						<div class="detail-text">
							<span>닉네임</span>
							<span><i class="fa-solid fa-person-walking"></i>&nbsp;&nbsp;&nbsp;<strong>20</strong></span>
						</div>
					</div>
					<div class="ranking-detail-bar">
						<img src="${pageContext.request.contextPath}/assets/images/sarang5.jpg">
						<div class="detail-text">
							<span>닉네임</span>
							<span><i class="fa-solid fa-person-walking"></i>&nbsp;&nbsp;&nbsp;<strong>20</strong></span>
						</div>
					</div>
					<div class="ranking-detail-bar">
						<img src="${pageContext.request.contextPath}/assets/images/sarang5.jpg">
						<div class="detail-text">
							<span>닉네임</span>
							<span><i class="fa-solid fa-person-walking"></i>&nbsp;&nbsp;&nbsp;<strong>20</strong></span>
						</div>
					</div>
					<div class="ranking-detail-bar">
						<img src="${pageContext.request.contextPath}/assets/images/sarang5.jpg">
						<div class="detail-text">
							<span>닉네임</span>
							<span><i class="fa-solid fa-person-walking"></i>&nbsp;&nbsp;&nbsp;<strong>20</strong></span>
						</div>
					</div>
				</div>
				
				<div class="map-sidebar segment-list-sidebar">
					<h2>비슷한 산책로 추천</h2>
					<div class="segments-sidebar" id="segments-sidebar">
						<ul>
							<li>
								<i class="fa-solid fa-location-dot fa-2x selected-maker"></i>
								<div>
									<span class="sideBar-title">천호공원 한바퀴</span><br>
									<span>0.87km</span>
									<span>30분</span>
								</div>
							</li>
							<li>
								<i class="fa-solid fa-location-dot fa-2x"></i>
								<div>
									<span class="sideBar-title">천호공원 한바퀴</span><br>
									<span>0.87km</span>
									<span>30분</span>
								</div>
							</li>
							<li>
								<i class="fa-solid fa-location-dot fa-2x"></i>
								<div>
									<span class="sideBar-title">천호공원 한바퀴</span><br>
									<span>0.87km</span>
									<span>30분</span>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- // map-container-right -->
		</div>
		<!-- // map-container -->
		
		<div class="segment-comments">
			<div class="comment-nav">
				<div>후기</div>
				<div class="selected">산책일지</div>
				<div>모임일지</div>
				<i class="fa-solid fa-caret-up"></i>
			</div>
			
			<div class="comment-container">
				<div class="comment-bar comment-meeting-bar">
					<div class="comment-number"><i class="fa-solid fa-person-walking"></i>&nbsp;1k</div>
					
					<div class="form-check meeting-check">
						<input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
						<label class="form-check-label" for="flexCheckDefault">내 일지만 보기</label>
					</div>
					
					<div class="btn-group" role="group" aria-label="Default button group">
						<button type="button" class="btn btn-outline-dark">인기순</button>
						<button type="button" class="btn btn-outline-dark">최신순</button>
					</div>
				</div>
				<!-- // comment-bar -->
				
				<div class="meetingList">
					<div class="meeting workLog">
						<div class="meetingTitle workTitle">
							<div class="left work-title1">0.87km</div>
							<div class="left work-title2">30분</div>
							<div class="right">2023/10/04</div>
						</div>
						<div class="meetingContent">
							<div class="meetingInfo left">
								<p class="info-border">나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기</p>
								<div class="img-info">
									<div class="img-info-detail">
										<img src="${pageContext.request.contextPath}/assets/images/sarang3.jpg">
										<div class="detail-text">
											<span>닉네임</span>
											<span>2023/10/11</span>
										</div>
										<span class="workLogLike"><i class="fa-solid fa-thumbs-up"></i>&nbsp;20</span>
										<%-- <div class="dog-img"><img src="${pageContext.request.contextPath}/assets/images/sarang3.jpg"></div>
										<div class="dog-img"><img src="${pageContext.request.contextPath}/assets/images/sarang3.jpg"></div> --%>
									</div>
								</div>
							</div>
							<div class="left work-img">
								<div class="comment-img">
									<img src="${pageContext.request.contextPath}/assets/images/sarang1.jpg">
									<div class="imgCount">3</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="meeting workLog">
						<div class="meetingTitle workTitle">
							<div class="left work-title1">0.87km</div>
							<div class="left work-title2">30분</div>
							<div class="right">2023/10/04</div>
						</div>
						<div class="meetingContent">
							<div class="meetingInfo left">
								<p class="info-none">내용이 없습니다.</p>
								<div class="img-info">
									<div class="img-info-detail">
										<img src="${pageContext.request.contextPath}/assets/images/sarang3.jpg">
										<div class="detail-text">
											<span>닉네임</span>
											<span>2023/10/11</span>
										</div>
										<span class="workLogLike"><i class="fa-solid fa-thumbs-up"></i>&nbsp;20</span>
									</div>
								</div>
							</div>
							<div class="left"></div>
						</div>
					</div>
					
					<div class="meeting workLog">
						<div class="meetingTitle workTitle">
							<div class="left work-title1">0.87km</div>
							<div class="left work-title2">30분</div>
							<div class="right">2023/10/04</div>
						</div>
						<div class="meetingContent">
							<div class="meetingInfo left">
								<p class="info-border">나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기</p>
								<div class="img-info">
									<div class="img-info-detail">
										<img src="${pageContext.request.contextPath}/assets/images/sarang3.jpg">
										<div class="detail-text">
											<span>닉네임</span>
											<span>2023/10/11</span>
										</div>
										<span class="workLogLike"><i class="fa-solid fa-thumbs-up"></i>&nbsp;20</span>
									</div>
								</div>
							</div>
							<div class="left"></div>
						</div>
					</div>
					
					<div class="meeting workLog">
						<div class="meetingTitle workTitle">
							<div class="left work-title1">0.87km</div>
							<div class="left work-title2">30분</div>
							<div class="right">2023/10/04</div>
						</div>
						<div class="meetingContent">
							<div class="meetingInfo left">
								<p class="info-border">나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기</p>
								<div class="img-info">
									<div class="img-info-detail">
										<img src="${pageContext.request.contextPath}/assets/images/sarang3.jpg">
										<div class="detail-text">
											<span>닉네임</span>
											<span>2023/10/11</span>
										</div>
										<span class="workLogLike"><i class="fa-solid fa-thumbs-up"></i>&nbsp;20</span>
									</div>
								</div>
							</div>
							<div class="left">
								<div class="comment-img">
									<img src="${pageContext.request.contextPath}/assets/images/sarang1.jpg">
									<div class="imgCount">3</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="meeting workLog">
						<div class="meetingTitle workTitle">
							<div class="left work-title1">0.87km</div>
							<div class="left work-title2">30분</div>
							<div class="right">2023/10/04</div>
						</div>
						<div class="meetingContent">
							<div class="meetingInfo left">
								<p class="info-border">나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기</p>
								<div class="img-info">
									<div class="img-info-detail">
										<img src="${pageContext.request.contextPath}/assets/images/sarang3.jpg">
										<div class="detail-text">
											<span>닉네임</span>
											<span>2023/10/11</span>
										</div>
										<span class="workLogLike"><i class="fa-solid fa-thumbs-up"></i>&nbsp;20</span>
									</div>
								</div>
							</div>
							<div class="left">
								<div class="comment-img">
									<img src="${pageContext.request.contextPath}/assets/images/sarang1.jpg">
									<div class="imgCount">3</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="meeting workLog">
						<div class="meetingTitle workTitle">
							<div class="left work-title1">0.87km</div>
							<div class="left work-title2">30분</div>
							<div class="right">2023/10/04</div>
						</div>
						<div class="meetingContent">
							<div class="meetingInfo left">
								<p class="info-border">나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기</p>
								<div class="img-info">
									<div class="img-info-detail">
										<img src="${pageContext.request.contextPath}/assets/images/sarang3.jpg">
										<div class="detail-text">
											<span>닉네임</span>
											<span>2023/10/11</span>
										</div>
										<span class="workLogLike"><i class="fa-solid fa-thumbs-up"></i>&nbsp;20</span>
									</div>
								</div>
							</div>
							<div class="left">
								<div class="comment-img">
									<img src="${pageContext.request.contextPath}/assets/images/sarang1.jpg">
									<div class="imgCount">3</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="meeting workLog">
						<div class="meetingTitle workTitle">
							<div class="left work-title1">0.87km</div>
							<div class="left work-title2">30분</div>
							<div class="right">2023/10/04</div>
						</div>
						<div class="meetingContent">
							<div class="meetingInfo left">
								<p class="info-border">나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기</p>
								<div class="img-info">
									<div class="img-info-detail">
										<img src="${pageContext.request.contextPath}/assets/images/sarang3.jpg">
										<div class="detail-text">
											<span>닉네임</span>
											<span>2023/10/11</span>
										</div>
										<span class="workLogLike"><i class="fa-solid fa-thumbs-up"></i>&nbsp;20</span>
									</div>
								</div>
							</div>
							<div class="left">
								<div class="comment-img">
									<img src="${pageContext.request.contextPath}/assets/images/sarang1.jpg">
									<div class="imgCount">3</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="meeting workLog">
						<div class="meetingTitle workTitle">
							<div class="left work-title1">0.87km</div>
							<div class="left work-title2">30분</div>
							<div class="right">2023/10/04</div>
						</div>
						<div class="meetingContent">
							<div class="meetingInfo left">
								<p class="info-border">나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기</p>
								<div class="img-info">
									<div class="img-info-detail">
										<img src="${pageContext.request.contextPath}/assets/images/sarang3.jpg">
										<div class="detail-text">
											<span>닉네임</span>
											<span>2023/10/11</span>
										</div>
										<span class="workLogLike"><i class="fa-solid fa-thumbs-up"></i>&nbsp;20</span>
									</div>
								</div>
							</div>
							<div class="left">
								<div class="comment-img">
									<img src="${pageContext.request.contextPath}/assets/images/sarang1.jpg">
									<div class="imgCount">3</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="meeting workLog">
						<div class="meetingTitle workTitle">
							<div class="left work-title1">0.87km</div>
							<div class="left work-title2">30분</div>
							<div class="right">2023/10/04</div>
						</div>
						<div class="meetingContent">
							<div class="meetingInfo left">
								<p class="info-border">나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기</p>
								<div class="img-info">
									<div class="img-info-detail">
										<img src="${pageContext.request.contextPath}/assets/images/sarang3.jpg">
										<div class="detail-text">
											<span>닉네임</span>
											<span>2023/10/11</span>
										</div>
										<span class="workLogLike"><i class="fa-solid fa-thumbs-up"></i>&nbsp;20</span>
									</div>
								</div>
							</div>
							<div class="left">
								<div class="comment-img">
									<img src="${pageContext.request.contextPath}/assets/images/sarang1.jpg">
									<div class="imgCount">3</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="meeting workLog">
						<div class="meetingTitle workTitle">
							<div class="left work-title1">0.87km</div>
							<div class="left work-title2">30분</div>
							<div class="right">2023/10/04</div>
						</div>
						<div class="meetingContent">
							<div class="meetingInfo left">
								<p class="info-border">나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기 나의 산책일지 일기</p>
								<div class="img-info">
									<div class="img-info-detail">
										<img src="${pageContext.request.contextPath}/assets/images/sarang3.jpg">
										<div class="detail-text">
											<span>닉네임</span>
											<span>2023/10/11</span>
										</div>
										<span class="workLogLike"><i class="fa-solid fa-thumbs-up"></i>&nbsp;20</span>
									</div>
								</div>
							</div>
							<div class="left work-img">
								<div class="comment-img">
									<img src="${pageContext.request.contextPath}/assets/images/sarang1.jpg">
									<div class="imgCount">3</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- // meetingList -->
			</div>
			<!-- // comment-container -->
		</div>
		<!-- // map-container -->
	</div>
</body>
<script type="text/javascript">
	var map = new naver.maps.Map('map');
</script>
</html>