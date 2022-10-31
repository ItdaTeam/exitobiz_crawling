package co.kr.exitobiz.Service.Mobile.impl;

import co.kr.exitobiz.Mappers.Mobile.CommunityMapper;
import co.kr.exitobiz.Service.Mobile.CommunityService;
import co.kr.exitobiz.Service.Mobile.FileService;
import co.kr.exitobiz.Vo.Mobile.CommunityVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityMapper communityMapper;

    private final FileService fileService;

    @Override
    public List<CommunityVo> getCommunityList(HashMap<String, Object> params) throws ParseException {
        return communityMapper.getCommunityList(params);
    }

    @Override
    public List<HashMap> getPopularCommunityList(CommunityVo communityVo) throws ParseException {
        return communityMapper.getPopularCommunityList(communityVo);
    }

    @Override
    public List<HashMap> getBlockList(CommunityVo communityVo) throws ParseException {
        return communityMapper.getBlockList(communityVo);
    }

    @Override
    public void delBlockUser(List<Object> params) throws ParseException {
        communityMapper.delBlockUser(params);
    }

    @Override
    public HashMap<String, Object> getCommunityDetail(CommunityVo communityVo) {
        return communityMapper.getCommunityDetail(communityVo);
    }

    @Override
    @Transactional
    public void insertCommunity(CommunityVo communityVo) throws ParseException {
        StringBuilder ImgPath = new StringBuilder();
        try{
            if(communityVo.getUpFile() != null){
                String fileName = fileService.fileNameGenerator(communityVo.getUpFile());
                HashMap<String, String> params = new HashMap<>();
                params.put("filePath", "/img/community/");
                params.put("fileName", fileName);

                ImgPath.append("https://exitobiz.co.kr/img/community/").append(fileName);
                try{
                    System.out.println("check");
                    fileService.uploadFile(communityVo.getUpFile(), params);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println("##########" + communityVo);

            communityMapper.insertCommunity(communityVo);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    @Transactional
    public void updateCommunity(CommunityVo communityVo) throws ParseException {
        StringBuilder ImgPath = new StringBuilder();

        try{
            if(communityVo.getUpFile() != null){
                String fileName = fileService.fileNameGenerator(communityVo.getUpFile());
                HashMap<String, String> params = new HashMap<>();
                params.put("filePath", "/img/community/");
                params.put("fileName", fileName);

                ImgPath.append("https://exitobiz.co.kr/img/community/").append(fileName);
                try{
                    fileService.uploadFile(communityVo.getUpFile(), params);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            communityMapper.updateCommunity(communityVo);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void deleteCommunity(CommunityVo communityVo) throws ParseException {
        communityMapper.deleteCommunity(communityVo);
    }

    @Override
    @Transactional
    public void declareCommunity(CommunityVo communityVo) throws ParseException {
        communityMapper.declareCommunity(communityVo);
    }

    @Override
    public void reviewViews(int id) {
        communityMapper.reviewViews(id);
    }

    @Override
    public int getNewId() {
        return communityMapper.getNewId();
    }
}
