package co.kr.exitobiz.Service.Mobile.impl;

import co.kr.exitobiz.Mappers.Mobile.CommunityMapper;
import co.kr.exitobiz.Service.Mobile.CommunityService;
import co.kr.exitobiz.Service.Mobile.FileService;
import co.kr.exitobiz.Vo.Mobile.CommunityVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
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
    public void delBlockUser(HashMap<String, Object> params) throws ParseException {
        communityMapper.delBlockUser(params);
    }

    @Override
    public void delAllBlockUser(HashMap<String, Object> params) throws ParseException {
        communityMapper.delAllBlockUser(params);
    }

    @Override
    public void insertBlock(HashMap<String, Object> params) throws ParseException {
        communityMapper.insertBlock(params);
    }

    @Override
    public void insertReport(HashMap<String, Object> params) throws ParseException {
        communityMapper.insertReport(params);
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

                //컨텐츠 내 이미지 저장
                ImgPath.append("https://exitobiz.co.kr/img/community/").append(fileName);
                try{
                    fileService.uploadFile(communityVo.getUpFile(), params);

                }catch (Exception e){
                    e.printStackTrace();
                }

                //다중첨부파일 저장
                if(communityVo.getAttachFile() != null){
                    final String url = "https://exitobiz.co.kr/file/community/";  // 임시
                    HashMap<String, String> fileParams = new HashMap<>();
                    ArrayList<String> fileArray = new ArrayList<>();

                    fileParams.put("filePath", "/img");
                    communityVo.getAttachFile().forEach(file ->{
                       try{
                            fileService.uploadFile(file, fileParams);
                       }catch(Exception e){
                           e.printStackTrace();
                       }
                    });
                }
            }
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
    public void insertFile(HashMap<String, Object> params) {
        communityMapper.insertFile(params);
    }

    @Override
    public List<HashMap> getFile(HashMap<String, Object> params) {
        return communityMapper.getFile(params);
    }

    @Override
    public void insertContentLike(HashMap<String, Object> params) {
        communityMapper.insertContentLike(params);
    }

    @Override
    public void updateTotalContentLike(HashMap<String, Object> params) {
        communityMapper.updateTotalContentLike(params);
    }

    @Override
    public List<HashMap> getCommentList(HashMap<String, Object> params) throws ParseException {
        return communityMapper.getCommentList(params);
    }

    @Override
    public List<HashMap> getRecommentList(HashMap<String, Object> params) throws ParseException {
        return communityMapper.getRecommentList(params);
    }

    @Override
    public void updateComment(HashMap<String, Object> params) throws ParseException {
        communityMapper.updateComment(params);
    }

    @Override
    public void insertComment(HashMap<String, Object> params) throws ParseException {
        communityMapper.insertComment(params);
    }

    @Override
    public void delComment(HashMap<String, Object> params) throws ParseException {
        communityMapper.delComment(params);
    }

    @Override
    public void reviewViews(int id) {
        communityMapper.reviewViews(id);
    }

    @Override
    public int getNewId() {
        return communityMapper.getNewId();
    }

    @Override
    public void insertCommentLike(HashMap<String, Object> params) {
        communityMapper.insertCommentLike(params);
    }

    @Override
    public void updateTotalCommentLike(HashMap<String, Object> params) {
        communityMapper.updateTotalCommentLike(params);
    }

    @Override
    public List<Map<String, Object>> getMyContent(Map<String, String> header) {
        return communityMapper.getMyContent(header);
    }

    @Override
    public List<Map<String, Object>> getMyComment(Map<String, String> header) {
        return communityMapper.getMyComment(header);
    }

    @Override
    public Map<String, Object> getTotalCnt(Map<String, Object> params) {
        return communityMapper.getTotalCnt(params);
    }

    @Override
    public Map<String, Object> deleteOne(Map<String, Object> params) {
        return communityMapper.deleteOne(params);
    }

}
