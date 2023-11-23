package com.runningdog.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runningdog.dao.TrailDao;
import com.runningdog.vo.CoordsVo;
import com.runningdog.vo.DogVo;
import com.runningdog.vo.ImagesVo;
import com.runningdog.vo.LocationVo;
import com.runningdog.vo.TrailCmtVo;
import com.runningdog.vo.TrailStarVo;
import com.runningdog.vo.TrailTagVo;
import com.runningdog.vo.TrailVo;
import com.runningdog.vo.UsersVo;
import com.runningdog.vo.WalkLogVo;
import com.runningdog.vo.WalkedDogVo;

@Service
public class TrailService {
	
	@Autowired TrailDao trailDao;
	
	// trailMain //////////////////////////////
	
	// 유저 설정 위치
	public LocationVo userLocation(int userNo) {
		System.out.println("TrailService.userLocation()");
		
		LocationVo locationVo = null;
		if(userNo != 0) {
			locationVo = trailDao.userLocation(userNo);
		} else {
			locationVo = trailDao.guestLocation();
		}
		
		return locationVo;
	}

	// 산책로 목록 ajax
	public Map<String, Object> trailListMap(Map<String, Object> fetchSet) {
		System.out.println("TrailService.trailListMap()");

		// 산책로 목록
		List<TrailVo> trailList = trailDao.trailList(fetchSet);
		// System.out.println("trailList : " + trailList);
		
		// 산책로 좌표 목록
		List<List<CoordsVo>> coordsList = new ArrayList<List<CoordsVo>>();
		for (TrailVo trailVo : trailList) {
			int trailNo = trailVo.getTrailNo();
			List<CoordsVo> coords = trailDao.coordsList(trailNo);
			coordsList.add(coords);
		}
		// System.out.println("coordsList : "  +coordsList);
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("trailList", trailList);
		listMap.put("coordsList", coordsList);

		return listMap;
	}
	
	// 산책로 툴팁 ajax
	public Map<String, Object> trailTooltip(int trailNo) {
		System.out.println("TrailService.trailTooltip()");
		
		// 산책로 정보
		TrailVo trailVo = trailDao.trailDetail(trailNo);
		
		String[] info = {"", ""};
		if(trailVo.getDistance() >= 1000) {
			info[0] = (int) Math.floor(trailVo.getDistance() / 1000) + "." + (int) Math.floor((trailVo.getDistance() % 1000) / 10) + "km";
		} else {
			info[0] = trailVo.getDistance() + "m";
		}
		
		int minute = (int) Math.floor(trailVo.getEta() / 60);
		if(60 <= minute) {
			int hour = (int) Math.floor(minute / 60);
        	minute = minute - (hour * 60);

        	info[1] +=  hour +  ":";
        } else {
        	info[1] +=  "0:";
        }
		info[1] += minute;
		
		// 유저 프로필
		ImagesVo userImg = trailDao.userImg(trailVo.getUsersVo().getUserNo());
		// 산책로 이용 정보
		int trailUsedCnt = trailDao.trailUsedCnt(trailNo);
		int trailStarCnt = trailDao.trailStarCnt(trailNo);
		int trailCmtCnt = trailDao.trailCmtCnt(trailNo);
		
		Map<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("trailVo", trailVo);
		infoMap.put("info", info);
		infoMap.put("userImg", userImg);
		infoMap.put("trailUsedCnt", trailUsedCnt);
		infoMap.put("trailStarCnt", trailStarCnt);
		infoMap.put("trailCmtCnt", trailCmtCnt);
		
		return infoMap;
	}

	// 산책일지 목록 ajax
	public List<WalkLogVo> walkLogList(Map<String, Object> fetchSet) {
		System.out.println("TrailService.walkLogList()");
		
		List<WalkLogVo> walkLogList = trailDao.walkLogList(fetchSet);
		// System.out.println("walkLogList : " + walkLogList);

		return walkLogList;
	}

	// 산책일지 좌표 ajax
	public List<CoordsVo> walkLogMap(WalkLogVo walkLogVo) {
		System.out.println("TrailService.walkLogMap()");
		
		int walkLogNo = walkLogVo.getWalkLogNo();
		List<CoordsVo> coords = trailDao.walkLogCoords(walkLogNo);
		// System.out.println("coords : "  +coords);
		
		return coords;
	}
	
	// trailForm //////////////////////////////
	
	// 산책일지 좌표
	public String walkLogCoords(WalkLogVo walkLogVo) throws JsonProcessingException {
		System.out.println("TrailService.walkLogCoords()");
		
		int walkLogNo = walkLogVo.getWalkLogNo();
		List<CoordsVo> coords = trailDao.walkLogCoords(walkLogNo);
		
		ObjectMapper mapper = new ObjectMapper();
		String coordsJson = mapper.writeValueAsString(coords);
		
		return coordsJson;
	}

	// 산책로 이름 중복확인 ajax
	public int confirmName(TrailVo trailVo) {
		System.out.println("TrailService.confirmName()");
		
		int selectCnt = trailDao.confirmName(trailVo);
		
		return selectCnt;
	}

	// 산책로 등록 ajax
	public int trailAdd(Map<String, Object> fetchSet) {
		System.out.println("TrailService.trailAdd()");
		
		int insertCnt = 0;
		String[] markers = {"parking", "restroom", "trashCan"};
		ObjectMapper mapper = new ObjectMapper();
		
		TrailVo trailVo = mapper.convertValue(fetchSet.get("trailVo"), TrailVo.class);
		Map<String, Boolean> infoCheck = (Map<String, Boolean>) fetchSet.get("infoCheck");
		Map<String, Map<String, Object>> infoMarker = (Map<String, Map<String, Object>>) fetchSet.get("infoMarker");
		ArrayList<Map<String, Object>> coordsList = (ArrayList<Map<String, Object>>) fetchSet.get("coordsList");
		ArrayList<String> tagArr = (ArrayList<String>) fetchSet.get("tagArr");
		int userNo = (int) fetchSet.get("userNo");
		UsersVo usersVo = new UsersVo();
		usersVo.setUserNo(userNo);
		trailVo.setUsersVo(usersVo);
		
		if(infoCheck.get(markers[0]) == true) {
			trailVo.setParking('T');
		} else {
			trailVo.setParking('F');
		}
		if(infoCheck.get(markers[1]) == true) {
			trailVo.setRestroom('T');
		} else {
			trailVo.setRestroom('F');
		}
		if(infoCheck.get(markers[2]) == true) {
			trailVo.setTrashCan('T');
		} else {
			trailVo.setTrashCan('F');
		}
		
		// 산책로 등록
		int trailInsertCnt = trailDao.trailInsert(trailVo);
		if(trailInsertCnt != 0) {
			System.out.println("산책로 등록 성공");
			
			if(tagArr.size() != 0) {
				for (String tag : tagArr) {
					TrailTagVo trailTagVo = new TrailTagVo();
					trailTagVo.setTrailVo(trailVo);
					trailTagVo.setTagName(tag);
					
					// 산책로 태그 등록
					trailDao.trailTagInsert(trailTagVo);
				}
			}
			
			for (Map<String, Object> coordsMap : coordsList) {
				CoordsVo coordsVo = new CoordsVo();
				coordsVo.setType("trail");
				coordsVo.setUseNo(trailVo.getTrailNo());
				coordsVo.setCoordOrder((int) coordsMap.get("coordOrder"));
				coordsVo.setLat((double) coordsMap.get("y"));
				coordsVo.setLng((double) coordsMap.get("x"));

				// 산책로 좌표 등록
				trailDao.trailCoordsInsert(coordsVo);
			}

			for(String marker : markers) {
				if(infoMarker.get(marker) != null) {
					// System.out.println("marker o : " + infoMarker.get(marker));
					
					CoordsVo coordsVo = new CoordsVo();
					coordsVo.setType(marker);
					coordsVo.setUseNo(trailVo.getTrailNo());
					coordsVo.setCoordOrder(1);
					coordsVo.setLat((double) infoMarker.get(marker).get("y"));
					coordsVo.setLng((double) infoMarker.get(marker).get("x"));
					
					// 정보 좌표 등록
					trailDao.trailCoordsInsert(coordsVo);
				}
			}
			insertCnt = 1;
		} else {
			System.out.println("산책로 등록 실패");
		}
		
		/*
		System.out.println("fetchSet : " + fetchSet);
		System.out.println("trailVo : " + trailVo);
		System.out.println("infoCheck : " + infoCheck);
		System.out.println("infoMarker : " + infoMarker);
		System.out.println("coordsList : " + coordsList);
		System.out.println("tagArr : " + tagArr);
		System.out.println("userNo : " + userNo);
		*/
		
		return insertCnt;
	}
	
	// trailDetail //////////////////////////////

	// 산책로 상세
	public Map<String, Object> trailDetail(TrailVo vo) throws JsonProcessingException {
		System.out.println("TrailService.trailDetail()");
		
		String[] info = {"", ""};
		String[] markers = {"parking", "restroom", "trashCan"};
		ObjectMapper mapper = new ObjectMapper();
		
		// 산책로 정보
		TrailVo trailVo = trailDao.trailDetail(vo.getTrailNo());
		System.out.println("trailVo : " + trailVo);
		
		if(trailVo.getDistance() >= 1000) {
			info[0] = (int) Math.floor(trailVo.getDistance() / 1000) + "." +(int) Math.floor((trailVo.getDistance() % 1000) / 10) + "km";
		} else {
			info[0] = trailVo.getDistance() + "m";
		}
		
		int minute = (int) Math.floor(trailVo.getEta() / 60);
		if(60 <= minute) {
			int hour = (int) Math.floor(minute / 60);
        	minute = minute - (hour * 60);

        	info[1] +=  hour +  "시간 ";
        }
		info[1] += minute + "분";
		
		// 유저 프로필
		ImagesVo userImg = trailDao.userImg(trailVo.getUsersVo().getUserNo());
		// 유저 이용수
		int userUsedCnt = trailDao.userUsedCnt(trailVo);
		// 산책로 이용 정보
		int trailUsedCnt = trailDao.trailUsedCnt(vo.getTrailNo());
		int trailStarCnt = trailDao.trailStarCnt(vo.getTrailNo());
		int trailCmtCnt = trailDao.trailCmtCnt(vo.getTrailNo());
		// 산책로 태그
		List<TrailTagVo> tagList = trailDao.tagList(vo.getTrailNo());
		// System.out.println("tagList : " + tagList);
		// 산책로 좌표
		List<CoordsVo> coords = trailDao.coordsList(vo.getTrailNo());
		String coordsJson = mapper.writeValueAsString(coords);
		
		List<CoordsVo> markerCoords = new ArrayList<CoordsVo>();
		if(trailVo.getParking() == 'T') {
			CoordsVo markerVo = new CoordsVo();
			markerVo.setType(markers[0]);
			markerVo.setUseNo(vo.getTrailNo());
			
			// 산책로 정보 좌표
			CoordsVo resultVo = trailDao.markerCoords(markerVo);
			markerCoords.add(resultVo);
		}
		if(trailVo.getRestroom() == 'T') {
			CoordsVo markerVo = new CoordsVo();
			markerVo.setType(markers[1]);
			markerVo.setUseNo(vo.getTrailNo());
			
			// 산책로 정보 좌표
			CoordsVo resultVo = trailDao.markerCoords(markerVo);
			markerCoords.add(resultVo);
		}
		if(trailVo.getTrashCan() == 'T') {
			CoordsVo markerVo = new CoordsVo();
			markerVo.setType(markers[2]);
			markerVo.setUseNo(vo.getTrailNo());
			
			// 산책로 정보 좌표
			CoordsVo resultVo = trailDao.markerCoords(markerVo);
			markerCoords.add(resultVo);
		}
		// System.out.println("markerCoords : " + markerCoords);
		String markersJson = mapper.writeValueAsString(markerCoords);
		
		Map<String, Object> detailMap = new HashMap<String, Object>();
		detailMap.put("trailVo", trailVo);
		detailMap.put("info", info);
		detailMap.put("userImg", userImg);
		detailMap.put("userUsedCnt", userUsedCnt);
		detailMap.put("trailUsedCnt", trailUsedCnt);
		detailMap.put("trailStarCnt", trailStarCnt);
		detailMap.put("trailCmtCnt", trailCmtCnt);
		detailMap.put("tagList", tagList);
		detailMap.put("coordsJson", coordsJson);
		detailMap.put("markersJson", markersJson);
		
		return detailMap;
	}

	// 유저 상세
	public Map<String, Object> userDetail(TrailVo trailVo) {
		System.out.println("TrailService.userDetail()");
		
		// 산책로 찜 확인
		int starChk = trailDao.userTrailStar(trailVo);
		// 유저 상세
		UsersVo usersVo = trailDao.userDetail(trailVo.getUsersVo().getUserNo());
		// 유저 프로필
		ImagesVo userImg = trailDao.userImg(trailVo.getUsersVo().getUserNo());
		// 유저 이용수
		int userUsedCnt = trailDao.userUsedCnt(trailVo);
		// 최근 산책일지 목록
		List<WalkLogVo> walkLogList = trailDao.userwalkLogList(trailVo);
		
		String[][] infoList = new String[walkLogList.size()][2];
		for (int i = 0; i < walkLogList.size(); i++) {
			
			String[] info = {"", ""};
			if(walkLogList.get(i).getDistance() >= 1000) {
				info[0] = (int) Math.floor(walkLogList.get(i).getDistance() / 1000) + "." +(int) Math.floor((walkLogList.get(i).getDistance() % 1000) / 10) + "km";
			} else {
				info[0] = walkLogList.get(i).getDistance() + "m";
			}
			
			int minute = (int) Math.floor(walkLogList.get(i).getLogTime() / 60);
			if(60 <= minute) {
				int hour = (int) Math.floor(minute / 60);
	        	minute = minute - (hour * 60);

	        	info[1] +=  hour +  "시간 ";
	        }
			info[1] += minute + "분";
			
			infoList[i][0] = info[0];
			infoList[i][1] = info[1];
	    }
		/*
		for (int i = 0; i < infoList.length; i++) {
		    for (int j = 0; j < infoList[i].length; j++) {
		        System.out.print(infoList[i][j] + " ");
		    }
		    System.out.println();
		}
		*/
		
		Map<String, Object> walkLogMap = new HashMap<String, Object>();
		walkLogMap.put("walkLogList", walkLogList);
		walkLogMap.put("infoList", infoList);
		
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("starChk", starChk);
		userMap.put("usersVo", usersVo);
		userMap.put("userImg", userImg);
		userMap.put("userUsedCnt", userUsedCnt);
		userMap.put("walkLogMap", walkLogMap);
		
		return userMap;
	}

	// 산책로 이용 랭킹
	public Map<String, Object> trailUserUsed(TrailVo trailVo) {
		System.out.println("TrailService.trailUserUsed()");
		
		// 유저 상세 목록
		List<UsersVo> userList = trailDao.userDetailList(trailVo.getTrailNo());
		
		List<ImagesVo> imgList = new ArrayList<ImagesVo>();
		List<Integer> usedCntList = new ArrayList<Integer>();
		for (int i = 0; i < userList.size(); i++) {
			trailVo.setUsersVo(userList.get(i));
			
			// 유저 프로필
			ImagesVo userImg = trailDao.userImg(userList.get(i).getUserNo());
			// 유저 이용수
			int userUsedCnt = trailDao.userUsedCnt(trailVo);
			
			imgList.add(userImg);
			usedCntList.add(userUsedCnt);
	    }
		
		Map<String, Object> userUsedMap = new HashMap<String, Object>();
		userUsedMap.put("userList", userList);
		userUsedMap.put("imgList", imgList);
		userUsedMap.put("usedCntList", usedCntList);
		
		return userUsedMap;
	}
	
	// 산책로 이용 시간대
	public String trailUseTime(TrailVo trailVo) throws JsonProcessingException {
		System.out.println("TrailService.trailUseTime()");
		
		List<Map<String,Object>> trailUseTime = trailDao.trailUseTime(trailVo.getTrailNo());
		System.out.println("trailUseTime : " + trailUseTime);
		
		ObjectMapper mapper = new ObjectMapper();
		String useTimeJson = mapper.writeValueAsString(trailUseTime);
		
		return useTimeJson;
	}

	// 후기 - 목록 / 갤러리
	public Map<String, Object> cmtListMap(Map<String, Object> fetchSet) {
		System.out.println("TrailService.cmtListMap()");
		
		List<TrailCmtVo> cmtList = trailDao.cmtList(fetchSet);
		// System.out.println("cmtList : " + cmtList);
		// 후기 전체 수
		int cmtCnt = trailDao.cmtCnt(cmtList.get(0).getTrailVo().getTrailNo());
		
		List<List<ImagesVo>> cmtImgList = new ArrayList<List<ImagesVo>>();
		List<ImagesVo> userImgList = new ArrayList<ImagesVo>();
		List<Integer> likeCntList = new ArrayList<Integer>();
		for (TrailCmtVo trailCmtVo : cmtList) {
			// 후기 이미지 목록
			List<ImagesVo> images = trailDao.cmtImages(trailCmtVo.getTrailCmtNo());
			// 유저 프로필
			ImagesVo userImg = trailDao.userImg(trailCmtVo.getUsersVo().getUserNo());
			// 후기 좋아요수
			int cmtLikeCnt = trailDao.cmtLikeCnt(trailCmtVo.getTrailCmtNo());
			
			if(CollectionUtils.isEmpty(images)) {
				ImagesVo vo = new ImagesVo();
				vo.setSaveName("noImg");
				vo.setImageOrder(0);
				images.add(vo);
			}
			cmtImgList.add(images);
			userImgList.add(userImg);
			likeCntList.add(cmtLikeCnt);
		}
		// System.out.println("cmtImgList : " + cmtImgList);
		// System.out.println("userImgList : " + userImgList);
		// System.out.println("likeCntList : " + likeCntList);
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("cmtList", cmtList);
		listMap.put("cmtCnt", cmtCnt);
		listMap.put("cmtImgList", cmtImgList);
		listMap.put("userImgList", userImgList);
		listMap.put("likeCntList", likeCntList);
		
		return listMap;
	}
	
	// 산책로 산책일지
	public Map<String, Object> logListMap(Map<String, Object> fetchSet) {
		System.out.println("TrailService.logListMap()");
		
		List<WalkLogVo> logList = trailDao.logList(fetchSet);
		// System.out.println("logList : " + logList);
		
		String[][] infoList = new String[logList.size()][2];
		List<ImagesVo> logImgList = new ArrayList<ImagesVo>();
		List<ImagesVo> userImgList = new ArrayList<ImagesVo>();
		List<ImagesVo> dogImgList = new ArrayList<ImagesVo>();
		List<Integer> logImgCntList = new ArrayList<Integer>();
		List<Integer> dogCntList = new ArrayList<Integer>();
		List<Integer> likeCntList = new ArrayList<Integer>();
		for (int i = 0; i < logList.size(); i++) {
			
			String[] info = {"", ""};
			if(logList.get(i).getDistance() >= 1000) {
				info[0] = (int) Math.floor(logList.get(i).getDistance() / 1000) + "." +(int) Math.floor((logList.get(i).getDistance() % 1000) / 10) + "km";
			} else {
				info[0] = logList.get(i).getDistance() + "m";
			}
			
			int minute = (int) Math.floor(logList.get(i).getLogTime() / 60);
			if(60 <= minute) {
				int hour = (int) Math.floor(minute / 60);
	        	minute = minute - (hour * 60);

	        	info[1] +=  hour +  "시간 ";
	        }
			info[1] += minute + "분";
			
			// 산책일지 이미지
			ImagesVo logImg = trailDao.logImg(logList.get(i).getWalkLogNo());
			// 유저 프로필
			ImagesVo userImg = trailDao.userImg(logList.get(i).getUsersVo().getUserNo());
			// 산책한 강아지
			List<DogVo> dogList = trailDao.walkedDog(logList.get(i).getWalkLogNo());
			
			if(CollectionUtils.isEmpty(dogList)) {
				DogVo dogVo = new DogVo();
				dogVo.setDogNo(0);
				dogList.add(dogVo);
			}
			
			ImagesVo dogImg = null;
			int dogCnt = 0;
			if(dogList.get(0).getDogNo() != 0) {
				// 강아지 프로필
				dogImg = trailDao.dogImg(dogList.get(0).getDogNo());
				// 강아지 수
				dogCnt = dogList.size();
			}

			// 산책일지 이미지 수
			int logImgCnt = trailDao.logImgCnt(logList.get(i).getWalkLogNo());
			// 산책일지 좋아요수
			int logLikeCnt = trailDao.logLikeCnt(logList.get(i).getWalkLogNo());
			
			if(logImg == null) {
				// 산책일지 맵 이미지
				ImagesVo vo = trailDao.logMapImg(logList.get(i).getWalkLogNo());
				logImg = vo;
			}
			if(logImg == null) {
				ImagesVo vo = new ImagesVo();
				vo.setSaveName("noImg");
				vo.setImageOrder(0);
				logImg = vo;
			}
			if(dogImg == null) {
				ImagesVo vo = new ImagesVo();
				vo.setSaveName("noImg");
				vo.setImageOrder(0);
				dogImg = vo;
			}
			
			infoList[i][0] = info[0];
			infoList[i][1] = info[1];
			
			logImgList.add(logImg);
			userImgList.add(userImg);
			dogImgList.add(dogImg);
			logImgCntList.add(logImgCnt);
			dogCntList.add(dogCnt);
			likeCntList.add(logLikeCnt);
	    }
		// System.out.println("logImgList : " + logImgList);
		// System.out.println("userImgList : " + userImgList);
		// System.out.println("dogImgList : " + dogImgList);
		// System.out.println("logImgCntList : " + logImgCntList);
		// System.out.println("dogCntList : " + dogCntList);
		// System.out.println("likeCntList : " + likeCntList);
		/*
		for (int i = 0; i < infoList.length; i++) {
		    for (int j = 0; j < infoList[i].length; j++) {
		        System.out.print(infoList[i][j] + " ");
		    }
		    System.out.println();
		}
		*/
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("logList", logList);
		listMap.put("logImgList", logImgList);
		listMap.put("userImgList", userImgList);
		listMap.put("dogImgList", dogImgList);
		listMap.put("logImgCntList", logImgCntList);
		listMap.put("dogCntList", dogCntList);
		listMap.put("likeCntList", likeCntList);
		listMap.put("infoList", infoList);
		
		return listMap;
	}

	// 산책로 후기 작성 ajax
	public int trailCmtAdd(TrailCmtVo trailCmtVo) {
		System.out.println("TrailService.trailCmtAdd()");
		
		int insertCnt = trailDao.trailCmtAdd(trailCmtVo);
		if(insertCnt != 0) {
			System.out.println("후기 등록 성공");
			
			return trailCmtVo.getTrailCmtNo();
		} else {
			return 0;
		}
	}
	
	// 후기 이미지 업로드
	public void trailCmtImgAdd(Map<String, MultipartFile> fileMap, int trailCmtNo) {
		System.out.println("TrailService.trailCmtImgAdd()");
		
		int index = 0;
		for (MultipartFile file : fileMap.values()) {
			if(!file.isEmpty()) {
				// 파일 정보
				String saveDir = "C:\\javaStudy\\rdimg\\trail";
				
				String orgName = file.getOriginalFilename();
				String exName = orgName.substring(orgName.lastIndexOf("."));
				
				String saveName = System.currentTimeMillis()
						+ UUID.randomUUID().toString()
						+ exName;
				
				String filePath = saveDir + "\\" + saveName;
				
				int fileSize = (int) file.getSize();
				
				ImagesVo imagesVo = new ImagesVo();
				imagesVo.setUseNo(trailCmtNo);
				imagesVo.setOrgName(orgName);
				imagesVo.setSaveName(saveName);
				imagesVo.setFilePath(filePath);
				imagesVo.setFileSize(fileSize);
				imagesVo.setImageOrder(index);
				
				// DB 연결
				// 후기 이미지 업로드
				int imgInsertCnt = trailDao.cmtImgAdd(imagesVo);
				if(imgInsertCnt == 1) {
					System.out.println("후기 이미지 업로드 성공");
					
					// 서버 파일 저장
					try {
						byte[] fileDate;
						fileDate = file.getBytes();
						
						OutputStream os = new FileOutputStream(filePath);
						BufferedOutputStream bos = new BufferedOutputStream(os);
						
						bos.write(fileDate);
						bos.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					index++;
				}
			}
        }
	}

	// 산책로 찜
	public int trailStarAdd(TrailVo trailStarVo) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
