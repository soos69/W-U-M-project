package com.hnpl.wum.require.mapper;

import com.hnpl.wum.require.dto.RequireDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RequireMapper {
    //리뷰 등록
    int addRequire(RequireDto requireDto);
    //리뷰 수정
    int updateRequire(RequireDto requireDto);
    //리뷰 삭제
    int deleteRequire(Map map);

    //전체 리뷰 리스트
    List<RequireDto> requireList(Map map);

    //페이징을 위한 카운트
    int countRequire();

    //리뷰 디테일
    RequireDto requireDetail(long requireSeq);

    //추천추가
    int insertRecommend(Map map);
    //추천삭제
    int removeRecommend(Map map);
    //추천수 변경
    int changeRecommend(Map map);
    //추천했는지 확인
    Long userCheck(Map map);
}
