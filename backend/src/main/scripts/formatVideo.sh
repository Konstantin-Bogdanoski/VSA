#!/bin/bash
# @author Konstantin Bogdanoski (konstantin.b@live.com)
# This script needs to be located in the parent folder of your videos,
# in my sistem it is located in /home/konstantin/Videos/

if [ $@ != "" ]; then
  echo "No location given"
  exit 0
fi

if [ "$2" == "0" ]; then # Chosen quality is Medium
  echo "[ENCODING] Medium quality"

  cmd="ffmpeg -y -i $1/$3  -g 48 -sc_threshold 0 -map 0:0 -map 0:1 -c:v:0 libx264 -vf scale=1280:720 -b:v:0 600k -c:a:0 aac -ac 2 -ab 128k -var_stream_map \"v:0,a:0\" -master_pl_name $3.m3u8 -f hls -hls_time 6 -hls_list_size 0 -hls_segment_filename \"$1/v%v/seg_%d.ts\" $1/v%v/prog_index.m3u8"
  echo "$cmd" >/home/konstantin/Videos/m3u8gen.sh
  echo "[ENCODING] Creating m3u8 file"
  sh /home/konstantin/Videos/m3u8gen.sh
  echo "[ENCODING] Created m3u8 file"
elif [ $2 == "1" ]; then
  echo "[ENCODING] Medium + Low Quality"
  cmd="ffmpeg -y -i $1/$3  -g 48 -sc_threshold 0 -map 0:0 -map 0:1 -map 0:0 -map 0:1 -c:v:0 libx264 -vf scale=1280:720 -b:v:0 600k -c:v:1 libx264 -vf scale=800:600 -b:v:1 300k -c:a:0 aac -ac 2 -ab 128k -c:a:1 aac -ac 2 -ab 64k -var_stream_map \"v:0,a:0 v:1,a:1\" -master_pl_name $3.m3u8 -f hls -hls_time 6 -hls_list_size 0 -hls_segment_filename \"$1/v%v/seg_%d.ts\" $1/v%v/prog_index.m3u8"
  echo "$cmd" >/home/konstantin/Videos/m3u8gen.sh
  sh /home/konstantin/Videos/m3u8gen.sh
elif [ "$2" == "2" ]; then
  echo "[ENCODING] Medium + High quality"
  cmd="ffmpeg -y -i $1/$3  -g 48 -sc_threshold 0 -map 0:0 -map 0:1 -map 0:0 -map 0:1 -c:v:0 libx264 -vf scale=1280:720 -b:v:0 600k -c:v:1 libx264 -vf scale=1920:1080 -b:v:1 1000k -c:a:0 aac -ac 2 -ab 128k -c:a:1 aac -ac 2 -ab 256k -var_stream_map \"v:0,a:0 v:1,a:1\" -master_pl_name $3.m3u8 -f hls -hls_time 6 -hls_list_size 0 -hls_segment_filename \"$1/v%v/seg_%d.ts\" $1/v%v/prog_index.m3u8"
  echo "$cmd" >/home/konstantin/Videos/m3u8gen.sh
  echo "[ENCODING] Creating m3u8 file"
  sh /home/konstantin/Videos/m3u8gen.sh
  echo "[ENCODING] Created m3u8 file"
elif [ "$2" == "3" ]; then
  echo "[ENCODING] Medium + High + Low quality"
  cmd="ffmpeg -y -i $1/$3  -g 48 -sc_threshold 0 -map 0:0 -map 0:1 -map 0:0 -map 0:1 -map 0:0 -map 0:1 -c:v:0 libx264 -vf scale=1280:720 -b:v:0 600k -c:v:1 libx264 -vf scale=800:600 -b:v:1 300k -c:v:2 libx264 -vf scale=1920:1080 -b:v:2 1000k -c:a:0 aac -ac 2 -ab 128k -c:a:1 aac -ac 2 -ab 64k -c:a:1 aac -ac 2 -ab 256k -var_stream_map \"v:0,a:0 v:1,a:1 v:2,a:2\" -master_pl_name $3.m3u8 -f hls -hls_time 6 -hls_list_size 0 -hls_segment_filename \"$1/v%v/seg_%d.ts\" $1/v%v/prog_index.m3u8"
  echo "$cmd" >/home/konstantin/Videos/m3u8gen.sh
  echo "[ENCODING] Creating m3u8 file"
  sh /home/konstantin/Videos/m3u8gen.sh
  echo "[ENCODING] Created m3u8 file"
fi

echo "[ENCODING] Finished encoding video"

rm "$1"/"$3"

exit 1
