package com.example.musicdemo.service;

import com.example.musicdemo.entity.Result;
import com.example.musicdemo.entity.Song;
import com.example.musicdemo.entity.SongIn;
import com.example.musicdemo.enums.ResultEnum;
import com.example.musicdemo.exception.SongException;
import com.example.musicdemo.repository.SongInRepository;
import com.example.musicdemo.repository.SongRepository;
import com.example.musicdemo.utils.ResultUtil;
import com.mpatric.mp3agic.*;
import org.apache.tomcat.jni.Directory;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;



@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;
    private String[] suffixes = new String[]{".mp3",".m4a",".lrc",".png",".jpg",".bmp",".webp",".tif",".gif",".mkv",".mp4"};

    /**
     * 根据fileName搜索歌曲信息
     */
    public Song findSongByFileName(String fileName) throws SongException{
        return songRepository.findByFileName(fileName);
    }
    /**
     * 根据fileName搜索歌曲信息
     */
    public List<Song> findSongAll(int page,int size) throws SongException{
        PageRequest pr = PageRequest.of(page,size);
        return songRepository.findAllByOrderByCreateDateDesc(pr);
    }

    /**
     * 模糊搜索歌曲
     */
    public List<Song> findSongBySearchName(String searchName,int page) throws SongException{
        PageRequest pr = PageRequest.of(page,10);
        return songRepository.findByFileNameLikeOrderByCreateDateDesc(pr,"%" + searchName + "%");
    }

    /**
     * 插入歌曲文件或修改
     */
    public Result<String> insertSong(MultipartFile file) throws SongException{
        File clientFile = new File("C:/musicDemo/song/" + file.getOriginalFilename());
        try {
            int code = -1;
            String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            for(String suffix : suffixes){
                if(fileSuffix.equals(suffix)){
                    code = 200;
                }
            }
            if(code == 200){
                file.transferTo(clientFile);
                getMessage(clientFile,null);
            }else {
                return ResultUtil.error(ResultEnum.SONG_INSERT_SUFFIX_ERROR.getCode(),ResultEnum.SONG_INSERT_SUFFIX_ERROR.getMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.SONG_INSERT_ERROR.getCode(),ResultEnum.SONG_INSERT_ERROR.getMsg());
        }
        return ResultUtil.success();
    }

    public Result<String> insertSong(MultipartFile file,String songName,String singer) throws SongException{
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File clientFile = new File("C:/musicDemo/song/" + singer + " - " + songName + fileSuffix);
        try {
            int code = -1;
            for(String suffix : suffixes){
                if(fileSuffix.equals(suffix)){
                    code = 200;
                }
            }
            if(code == 200){
                file.transferTo(clientFile);
                getMessage(clientFile,new String[]{singer,songName});
            }else {
                return ResultUtil.error(ResultEnum.SONG_INSERT_SUFFIX_ERROR.getCode(),ResultEnum.SONG_INSERT_SUFFIX_ERROR.getMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.error(ResultEnum.SONG_INSERT_ERROR.getCode(),ResultEnum.SONG_INSERT_ERROR.getMsg());
        }
        return ResultUtil.success();
    }
    public void insertDefault(File file) throws SongException{
        getMessage(file,null);
    }
    //遍历整个文件里所有的歌曲
    public void getMessage(File file,String[] strings){
        System.out.println(file.getName() + "  ");
        if(file != null){
            if(file.isDirectory()){
                File f[] = file.listFiles();
                if( f != null){
                    for(int i = 0;i < f.length;i++){
                        getMessage(f[i],strings);// 因为给的路径有可能是目录，所以，继续判断
                    }
                }
            }else{
                String allFileName = file.getName();
                String suffix = file.getName().substring(file.getName().lastIndexOf("."));
                String fileName = file.getName().substring(0,file.getName().lastIndexOf("."));
                Song song = songRepository.findByFileName(fileName);
                if(song == null){
                    song = new Song();
                }
                song.setFileName(fileName);
                song.setCommentNum(song.getCommentNum());
                if(suffixes[0].equals(suffix) || suffixes[1].equals(suffix)){
                    try {
                        if(strings == null) {
                            Mp3File mp3File = new Mp3File(file.toString());
                            if (mp3File.hasId3v2Tag()) {
                                ID3v2 id3v2Tag = mp3File.getId3v2Tag();
                                if (id3v2Tag.getTitle() == null || id3v2Tag.getArtist() == null) {
                                    setSongNameAndSinger(fileName, song);
                                } else {
                                    song.setSongName(id3v2Tag.getTitle());
                                    song.setSinger(id3v2Tag.getArtist());
                                }
                            } else if (mp3File.hasId3v1Tag()) {
                                ID3v1 id3v1Tag = mp3File.getId3v1Tag();
                                if (id3v1Tag.getTitle() == null || id3v1Tag.getArtist() == null) {
                                    setSongNameAndSinger(fileName, song);
                                } else {
                                    song.setSongName(id3v1Tag.getTitle());
                                    song.setSinger(id3v1Tag.getArtist());
                                }
                            } else {
                                setSongNameAndSinger(fileName, song);
                            }
                        }else{
                            song.setSinger(strings[0]);
                            song.setSongName(strings[1]);
                        }
                        //"http://" + Uri.encode(data.getImg(), "-![.:/,%?&=]")
                        song.setPlayUrl("http://47.107.232.78:8080/songPath/song/"+ file.getName());
                        songRepository.save(song);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(suffixes[2].equals(suffix)){
                    song.setLyrics(converfile(file));
                    songRepository.save(song);
                }else if(suffixes[3].equals(suffix) || suffixes[4].equals(suffix) || suffixes[5].equals(suffix) || suffixes[6].equals(suffix) || suffixes[7].equals(suffix) || suffixes[8].equals(suffix)){
                    song.setImg("http://47.107.232.78:8080/songPath/song/" + allFileName);
                    songRepository.save(song);
                }else if(suffixes[9].equals(suffix) || suffixes[10].equals(suffix)){
                    song.setMv("http://47.107.232.78:8080/songPath/song/" + allFileName);
                    songRepository.save(song);
                }
            }
        }
    }

    public void setSongNameAndSinger(String fileName,Song song){
        String[] fileNames = fileName.split(" - ");
        song.setSinger(fileNames[0]);
        song.setSongName(fileNames[1]);
    }

    public String converfile(File file){
        FileInputStream fis=null;
        BufferedInputStream bis=null;
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            bis.mark(4);
            byte[] first3bytes = new byte[3];
            //找到文档的前三个字节并自动判断文档类型。
            bis.read(first3bytes);
            bis.reset();
            if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB && first3bytes[2] == (byte) 0xBF) {// utf-8
                reader = new BufferedReader(new InputStreamReader(bis, "utf-8"));
            } else if (first3bytes[0] == (byte) 0xFF && first3bytes[1] == (byte) 0xFE) {
                reader = new BufferedReader(new InputStreamReader(bis, "unicode"));
            } else if (first3bytes[0] == (byte) 0xFE && first3bytes[1] == (byte) 0xFF) {
                reader = new BufferedReader(new InputStreamReader(bis,"utf-16be"));
            } else {
                reader = new BufferedReader(new InputStreamReader(bis, "GBK"));
            }
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    /**
     * 删除歌曲
     */
    @Transactional
    public Result<String> deleteFileName(String fileName) throws SongException{
        Song song = songRepository.findByFileName(fileName);
        //找到相关文件删除
        int code = -1;
        if(song != null) {
            File[] files = new File[suffixes.length];
            for(int i = 0;i < files.length;i++){
                files[i] = new File("C:/musicDemo/song/" + fileName + suffixes[i]);
            }
            for(File file : files){
                if(file.exists()){
                    if(file.delete()){
                        code = 200;
                    }
                }
            }
            int i = songRepository.deleteByFileName(fileName);
            if(code == 200){
                if(i == 1){
                    return ResultUtil.success("相关文件和数据已删除");
                }else{
                    return ResultUtil.success("相关文件已删除,但数据删除失败,请重新执行");
                }
            }else{
                if(i == 1){
                    return ResultUtil.success("相关数据已删除,但文件删除失败,请重新执行");
                }else{
                    return ResultUtil.success("文件删除失败,数据删除失败");
                }
            }
        }else{
            return ResultUtil.success("找不到相关数据");
        }
    }
}
