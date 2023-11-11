package com.runningdog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.runningdog.vo.CoordsVo;
import com.runningdog.vo.ImagesVo;
import com.runningdog.vo.LocationVo;
import com.runningdog.vo.TrailVo;
import com.runningdog.vo.WalkLogVo;

@Repository
public class TrailDao {

	@Autowired
	private SqlSession sqlSession;
	
	// trailMain //////////////////////////////
	
	// 유저 설정 위치
	public LocationVo userLocation(int userNo) {
		System.out.println("TrailDao.userLocation()");
		
		LocationVo locationVo = sqlSession.selectOne("walkTrail.userLocation", userNo);
		
		return locationVo;
	}

	// 게스트 설정 위치
	public LocationVo guestLocation() {
		System.out.println("TrailDao.guestLocation()");
		
		LocationVo locationVo = sqlSession.selectOne("walkTrail.guestLocation");
		
		return locationVo;
	}
	
	// 산책로 추천 / 등록 목록
	public List<TrailVo> trailList(Map<String, Object> fetchSet) {
		System.out.println("TrailDao.trailList()");
		
		List<TrailVo> trailList =  sqlSession.selectList("walkTrail.trailList", fetchSet);
		
		return trailList;
	}

	// 산책로 찜 목록
	public List<TrailVo> trailStarList(Map<String, Object> fetchSet) {
		System.out.println("TrailDao.trailStarList()");
		
		List<TrailVo> trailList =  sqlSession.selectList("walkTrail.trailStarList", fetchSet);
		
		return trailList;
	}
	
	// 산책로 좌표 목록
	public List<CoordsVo> coordsList(int trailNo) {
		// System.out.println("TrailDao.coordsList()");
		
		List<CoordsVo> coords = sqlSession.selectList("walkTrail.coordsList", trailNo);
		
		return coords;
	}

	// 산책로 정보
	public TrailVo trailInfo(int trailNo) {
		// System.out.println("TrailDao.trailInfo()");
		
		TrailVo trailVo = sqlSession.selectOne("walkTrail.trailInfo", trailNo);
		
		return trailVo;
	}
	
	// 산책로 유저 프로필
	public ImagesVo trailUserImg(int userNo) {
		// System.out.println("TrailDao.trailUserImg()");
		
		ImagesVo imagesVo = sqlSession.selectOne("walkTrail.trailUserImg", userNo);
		
		return imagesVo;
	}

	// 산책로 이용자수
	public int trailUsedCnt(int trailNo) {
		// System.out.println("TrailDao.trailUsedCnt()");
		
		int trailUsedCnt = sqlSession.selectOne("walkTrail.trailUsedCnt", trailNo);
		
		return trailUsedCnt;
	}
	
	// 산책로 찜 갯수
	public int trailStarCnt(int trailNo) {
		// System.out.println("TrailDao.trailStarCnt()");
		
		int trailStarCnt = sqlSession.selectOne("walkTrail.trailStarCnt", trailNo);
		
		return trailStarCnt;
	}
	
	// 산책로 후기 갯수
	public int trailCmtCnt(int trailNo) {
		// System.out.println("TrailDao.trailCmtCnt()");
		
		int trailCmtCnt = sqlSession.selectOne("walkTrail.trailCmtCnt", trailNo);
		
		return trailCmtCnt;
	}

	// 산책일지 목록
	public List<WalkLogVo> walkLogList(Map<String, Object> fetchSet) {
		System.out.println("TrailDao.walkLogList()");
		
		List<WalkLogVo> walkLogList =  sqlSession.selectList("walkTrail.walkLogList", fetchSet);
		
		return walkLogList;
	}

	// 산책일지 좌표
	public List<CoordsVo> coords(int walkLogNo) {
		System.out.println("TrailDao.coords()");
		
		List<CoordsVo> coords = sqlSession.selectList("walkTrail.coords", walkLogNo);
		
		return coords;
	}

}
