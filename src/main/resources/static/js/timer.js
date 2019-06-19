//var limit = 900000;
//    var leftTime;
//    var startTime = new Date();
//
//    setInterval('showClock2()',1000);
//
//    function set2fig(num) {
//       // 桁数が1桁だったら先頭に0を加えて2桁に調整する
//       var ret;
//       if( num < 10 ) { ret = "0" + num; }
//       else { ret = num; }
//       return ret;
//    }
//
//    function showClock2() {
//       var nowTime = new Date();
//       var nowHour = set2fig( nowTime.getHours() );
//       var nowMin  = set2fig( nowTime.getMinutes() );
//       var nowSec  = set2fig( nowTime.getSeconds() );
//       var msg = "現在時刻は、" + nowHour + ":" + nowMin + ":" + nowSec + " です。";
//       document.getElementById("RealtimeClockArea2").innerHTML = msg;
//
//       var nowTimeMs = (nowMin*60+nowSec)*1000;
//
//       while(nowTimeMs > limit){
//            nowTimeMs -= limit;
//       }
//       leftTime = limit - nowTimeMs;
//
//       var leftMin;
//       var leftSec;
//       if(leftTime < 60000){
//            leftMin = 0;
//            leftSec = leftTime / 1000;
//       } else {
//            leftMin = Math.floor(leftTime / 60000);
//            leftSec = (leftTime % 60000);
//       }
//       var show;
//       if(leftMin == 0){
//            show = leftSec + "秒"
//       } else {
//            show = leftMin + "分" + leftSec + "秒"
//       }
//
//       document.getElementById("RealtimeClockArea3").innerHTML = show;
//
//       setTimeout(complete,leftTime);
//
//    }
//
//    var complete = function(leftTime){
//            console.log(leftTime);
//            location.href="http://localhost:8080/top/matching/complete";
//    };


//window.onload(timerRun());
//
//function timerRun(){
//    setTimeout("pageGo()", 3000);
//}
//
function pageGo(){
    location.href="/top/matching/complete";
}

// カウントダウン処理
function set2fig(num) {
   // 数値が1桁だったら2桁の文字列にして返す
   var ret;
   if( num < 10 ) { ret = "0" + num; }
   else { ret = num; }
   return ret;
}
function isNumOrZero(num) {
   // 数値でなかったら0にして返す
   if( isNaN(num) ) { return 0; }
   return num;
}
function showCountdown() {
   // 現在日時を数値(1970-01-01 00:00:00からのミリ秒)に変換
   var nowDate = new Date();
   var dnumNow = nowDate.getTime();

   // 指定日時を数値(1970-01-01 00:00:00からのミリ秒)に変換
   var inputYear  = nowDate.getFullYear();
   var inputMonth = nowDate.getMonth();
   var inputDate  = nowDate.getDate();

   console.log(inputYear);

   var inputHour  = nowDate.getHours();

   var inputMin = 0;
   if(nowDate.getMinutes() < 1){
        inputMin = 15;
   }else if(nowDate.getMinutes() < 30){
        inputMin = 30;

   }else if(nowDate.getMinutes() < 45){
        inputMin = 45;
   }else if(nowDate.getMinutes()< 59 && nowDate.getSeconds()< 59){
         inputMin = 00;
   }

   //var inputMin   = 28;
   var inputSec   = 00;

   var targetDate = new Date(
            isNumOrZero(inputYear),
            isNumOrZero(inputMonth),
            isNumOrZero(inputDate),
            isNumOrZero(inputHour),
            isNumOrZero(inputMin),
            isNumOrZero(inputSec)
            );
   var dnumTarget = targetDate.getTime();

   // 表示を準備
   var dlYear  = targetDate.getFullYear();
   var dlMonth = targetDate.getMonth() + 1;
   var dlDate  = targetDate.getDate();
   var dlHour  = targetDate.getHours();
   var dlMin   = targetDate.getMinutes();
   var dlSec   = targetDate.getSeconds();
   var msg1 = set2fig(dlMin) + ":" + set2fig(dlSec);

   // 引き算して日数(ミリ秒)の差を計算
   var diff2Dates = dnumTarget - dnumNow;
   if( dnumTarget < dnumNow ) {
      // 期限が過ぎた場合は -1 を掛けて正の値に変換
      pageGo();
      diff2Dates *= -1;
   }

   // 差のミリ秒を、日数・時間・分・秒に分割
   var dDays  = diff2Dates / ( 1000 * 60 * 60 * 24 );   // 日数
   diff2Dates = diff2Dates % ( 1000 * 60 * 60 * 24 );
   var dHour  = diff2Dates / ( 1000 * 60 * 60 );   // 時間
   diff2Dates = diff2Dates % ( 1000 * 60 * 60 );
   var dMin   = diff2Dates / ( 1000 * 60 );   // 分
   diff2Dates = diff2Dates % ( 1000 * 60 );
   var dSec   = diff2Dates / 1000;   // 秒
   var msg2 = Math.floor(dMin) + "分"
            + Math.floor(dSec) + "秒";

   // 表示文字列の作成
   var msg;
   if( dnumTarget > dnumNow ) {
      // まだ期限が来ていない場合
      msg = "あと" + msg2 + "です。";
   }
   else {
      // 期限が過ぎた場合
      msg = msg1 + "は、既に" + msg2 + "前に過ぎました。";
   }

   // 作成した文字列を表示
   document.getElementById("RealtimeClockArea3").innerHTML = msg;
}
// 1秒ごとに実行
setInterval('showCountdown()',1000);

