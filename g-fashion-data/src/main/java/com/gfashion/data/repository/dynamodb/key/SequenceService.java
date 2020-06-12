package com.gfashion.data.repository.dynamodb.key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;


@Service
public class SequenceService {

    @Autowired
    RedisConnectionFactory factory;

    private RedisAtomicLong gfContactUsId(LocalDateTime date, String country, String city){
        String yyyyMMdd = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return new RedisAtomicLong("GC"+yyyyMMdd+country+city, factory);

    }

    private RedisAtomicLong gfContactUsLogSeq(LocalDateTime date){
        String yyyyMMddHHmmss = date.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return new RedisAtomicLong("GCLS"+yyyyMMddHHmmss, factory);
    }

    /**
     * gfContactUs.id格式：GC+当天日期串+国市id+redis自增序号，比如GC201909180101000001
     * @return
     */
    public String getGfContactUsId(LocalDateTime date, String country, String city){

        StringBuffer stringBuffer = new StringBuffer();

        RedisAtomicLong redisAtomicLong = gfContactUsId(date,country,city);

        stringBuffer.append(redisAtomicLong.getKey());

        redisAtomicLong.expire(24, TimeUnit.HOURS);

        long count = redisAtomicLong.incrementAndGet();

        int numofZero = 6 - String.valueOf(count).length();

        for(int i=0;i<numofZero;i++){
            stringBuffer.append("0");
        }

        StringBuffer transactionId = stringBuffer.append(count);

        return transactionId.toString();
    }

    /**
     * gfContactUsLog.seq格式：GCLS+当天日期串+redis自增序号，比如GCLS20190918120000000001
     * @return
     */
    public String getGfContactUsLogSeq(LocalDateTime date){

        StringBuffer stringBuffer = new StringBuffer();

        RedisAtomicLong redisAtomicLong = gfContactUsLogSeq(date);

        stringBuffer.append(redisAtomicLong.getKey());

        redisAtomicLong.expire(24, TimeUnit.HOURS);

        long count = redisAtomicLong.incrementAndGet();

        int numofZero = 6 - String.valueOf(count).length();

        for(int i=0;i<numofZero;i++){
            stringBuffer.append("0");
        }

        StringBuffer transactionId = stringBuffer.append(count);

        return transactionId.toString();
    }

}
