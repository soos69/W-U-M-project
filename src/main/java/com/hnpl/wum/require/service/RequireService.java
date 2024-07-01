package com.hnpl.wum.require.service;

import com.hnpl.wum.require.dto.RequireDto;
import com.hnpl.wum.require.mapper.RequireMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RequireService {
    @Autowired
    private RequireMapper requireMapper;

    public List<RequireDto> selectList(Map map){
        return requireMapper.requireList(map);
    }
    public int countRequire(){
        return requireMapper.countRequire();
    }
    public RequireDto requireDetail(Long requireSeq){
        return requireMapper.requireDetail(requireSeq);
    }
    public int insertRequire(RequireDto requireDto){
        return requireMapper.addRequire(requireDto);
    }
    public int updateRequire(RequireDto requireDto){
        return requireMapper.updateRequire(requireDto);
    }
    public int deleteRequire(Map map){
        return requireMapper.deleteRequire(map);
    }
    public void recommend(Long requireSeq,Long userSeq){
        RequireDto dto = requireDetail(requireSeq);
        Map map =new HashMap<>();
        map.put("userSeq",userSeq);
        map.put("requireSeq",requireSeq);
        int recommend;

        Long requireLikeSeq = requireMapper.userCheck(map);

        if(requireLikeSeq!=null){
            recommend=dto.getRequireLike()-1;
            map.put("likeSeq",requireLikeSeq);
            requireMapper.removeRecommend(map);
        }else {
            recommend=dto.getRequireLike()+1;
            requireMapper.insertRecommend(map);
        }

        map.put("requireLike",recommend);

        requireMapper.changeRecommend(map);
    }
}
