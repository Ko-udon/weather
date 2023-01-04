package zerobase.weather.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.weather.domain.Diary;
import zerobase.weather.service.DiaryService;

@RestController
@RequiredArgsConstructor
public class DiaryController {

  private final DiaryService diaryService;

  @ApiOperation(value = "다이어리 생성", notes = "이것은 노트")
  @PostMapping("/create/diary")
  void createDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date,
      @RequestBody String text) {
    diaryService.createDiary(date, text);
  }

  @ApiOperation(value = "선택한 날짜의 모든 일기 데이터를 가져옵니다")
  @GetMapping("/read/diary")
  List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
    return diaryService.readDiary(date);
  }

  @ApiOperation(value = "선택한 기간중의 모든 일기 데이터를 가져옵니다")
  @GetMapping("read/diaries")
  List<Diary> readDiaries(@RequestParam @DateTimeFormat(iso = ISO.DATE) @ApiParam(value = "조회일 기간 첫번째 날 : yyyy-MM-dd",example = "2022-02-20") LocalDate startDate,
      @RequestParam @DateTimeFormat(iso = ISO.DATE) @ApiParam(value = "조회일 마지막 날 : yyyy-MM-dd",example = "2022-02-20") LocalDate endDate) {
    return diaryService.readDiaries(startDate, endDate);
  }

  @PutMapping("/update/diary")
  void updateDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date,
      @RequestBody String text) {
    diaryService.updateDiary(date, text);
  }

  @DeleteMapping("/delete/diary")
  void deleteDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
    diaryService.deletediary(date);
  }


}
