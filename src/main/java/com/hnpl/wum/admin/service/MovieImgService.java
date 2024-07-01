package com.hnpl.wum.admin.service;

import com.hnpl.wum.admin.dto.MovieDto;
import com.hnpl.wum.admin.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MovieImgService {

    @Value("${movieImgLocation}")
    private String movieImgLocation;

    private final FileService fileService;
    private final MovieMapper movieMapper;

    public void saveMoviePoster(MovieDto movieDto, MultipartFile movieImgFile) throws Exception {

        String oriImgName = movieImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(movieImgLocation, oriImgName, movieImgFile.getBytes());

            imgUrl = "/images/movie/" + imgName;
        }

        movieDto.setPoster(imgUrl);

        movieMapper.insertMovie(movieDto);
    }

    //이미지 변경
    public void updateMoviePoster(Long movieSeq, MultipartFile movieImgFile) throws Exception {
        if(!movieImgFile.isEmpty()) {
            MovieDto savedMovieImg = movieMapper.movieDetail(movieSeq);

            //해당하는 내용 지우기
            if (!StringUtils.isEmpty(savedMovieImg.getPoster())) {
                fileService.deleteFile(movieImgLocation + "/" + savedMovieImg.getPoster());
            }

            String oriImgName = movieImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(movieImgLocation, oriImgName, movieImgFile.getBytes());
            String imgUrl = "/images/movie/" + imgName;

            savedMovieImg.setPoster(imgUrl);

            movieMapper.updateMovie(savedMovieImg);
        }
    }
}
