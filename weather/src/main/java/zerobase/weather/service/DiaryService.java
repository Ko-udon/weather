package zerobase.weather.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.vm.ci.meta.Local;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.Diary;
import zerobase.weather.repository.DiaryRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class DiaryService {
  private final DiaryRepository diaryRepository;

  @Value("${openweathermap.key}")  //yml, properties등 속성 파일에서 값을 지정할 수 있음
  private String apiKey;
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public void createDiary(LocalDate date, String text){
    //open weather map에서 날씨 데이터 가져오기
    String weatherData = getWeatherString();
    
   //받아온 날씨 값 json형태로 파싱하기
    Map<String,Object> parseWeather = parseWeather(weatherData);

    //파싱된 데이터 + 일기 값 우리 db로 넣기
    Diary diary = new Diary();
    diary.setWeather(parseWeather.get("main").toString());
    diary.setIcon(parseWeather.get("icon").toString());
    diary.setTemperature((Double) parseWeather.get("temp"));
    diary.setText(text);
    diary.setDate(date);

    diaryRepository.save(diary);
  }

  @Transactional(readOnly = true)
  public List<Diary> readDiary(LocalDate date) {
    return diaryRepository.findAllByDate(date);
  }

  public List<Diary> readDiaries(LocalDate startDate, LocalDate endDate){
    return diaryRepository.findAllByDateBetween(startDate,endDate);
  }

  public void updateDiary(LocalDate date, String text) {
    Diary nowDiary=diaryRepository.getFirstByDate(date);
    nowDiary.setText(text);
    diaryRepository.save(nowDiary);
  }

  public void deletediary(LocalDate date) {
    diaryRepository.deleteAllByDate(date);

  }



  private String getWeatherString()  {
    String apiUrl="https://api.openweathermap.org/data/2.5/weather?q=seoul&appid="+apiKey;
    try{
      URL url=new URL(apiUrl);
      HttpURLConnection connection=(HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      int responseCode=connection.getResponseCode();
      BufferedReader br;
      if(responseCode==200){
        br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
      }else{
        br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
      }
      String inputLine;
      StringBuilder response=new StringBuilder();
      while((inputLine=br.readLine()) != null){
        response.append(inputLine);
      }
      br.close();

      return response.toString();

    }catch (Exception e){
      return "failed to get response";
    }


  }

  private Map<String, Object> parseWeather(String jsonString){
    JSONParser jsonParser=new JSONParser();
    JSONObject jsonObject;

    try{
      jsonObject = (JSONObject) jsonParser.parse(jsonString);
    }catch (ParseException e){
      throw new RuntimeException(e);

    }
    Map<String,Object> resultMap = new HashMap<>();

    JSONObject mainData=(JSONObject) jsonObject.get("main");
    resultMap.put("temp",mainData.get("temp"));
    JSONArray weatherArray=(JSONArray) jsonObject.get("weather");
    JSONObject weatherData=(JSONObject) weatherArray.get(0);
    resultMap.put("main",weatherData.get("main"));
    resultMap.put("icon",weatherData.get("icon"));

    return resultMap;

  }



}
