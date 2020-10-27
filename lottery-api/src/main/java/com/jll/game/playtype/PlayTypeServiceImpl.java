package com.jll.game.playtype;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jll.common.cache.CacheRedisService;
import com.jll.common.constants.Constants;
import com.jll.common.constants.Message;
import com.jll.entity.PlayType;
import com.jll.entity.PlayTypeNum;
import com.jll.entity.display.BitColumn;

@Configuration
@PropertySource("classpath:sys-setting.properties")
@Service
@Transactional
public class PlayTypeServiceImpl implements PlayTypeService
{
	private Logger logger = Logger.getLogger(PlayTypeServiceImpl.class);

	@Resource
	PlayTypeDao playTypeDao;
	@Resource
	CacheRedisService cacheRedisService;

	
	@Override
	public List<PlayType> queryPlayType(String lotteryType) {
		List<PlayType> playTypes = playTypeDao.queryByLotteryType(lotteryType);
		return playTypes;
	}
	//添加
	@Override
	public Map<String,Object> addPlayType(PlayType playType) {
		Map<String,Object> map=new HashMap<String,Object>();
		boolean isNo=this.isNoPlayType(playType);
		if(!isNo) {
			Integer seq=playTypeDao.getCountSeq(playType.getLotteryType())+1;
			playType.setSeq(seq);
	        playType.setCreateTime(new Date());
			playTypeDao.addPlayType(playType);
			map.clear();
			map.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		}else {
			map.clear();
			map.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			map.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_PLAYTYPE_ALREADY_EXISTS.getCode());
			map.put(Message.KEY_ERROR_MES, Message.Error.ERROR_PLAYTYPE_ALREADY_EXISTS.getErrorMes());
		}
		return map;
	}
	//修改状态
	@Override
	public Map<String,Object> updateState(Integer id, Integer state) {
		Map<String,Object> map=new HashMap<String,Object>();
		boolean isNo=this.isNoPlayType(id);
		if(isNo) {
			playTypeDao.updateState(id, state);
			map.clear();
			map.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		}else {
			map.clear();
			map.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			map.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_PLAYTYPE_DOES_NOT_EXIST.getCode());
			map.put(Message.KEY_ERROR_MES, Message.Error.ERROR_PLAYTYPE_DOES_NOT_EXIST.getErrorMes());
		}
		return map;
	}
	//设置是否隐藏
	@Override
	public Map<String,Object> updateIsHidden(Integer id, Integer isHidden) {
		Map<String,Object> map=new HashMap<String,Object>();
		boolean isNo=this.isNoPlayType(id);
		if(isNo) {
			playTypeDao.updateIsHidden(id, isHidden);
			map.clear();
			map.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		}else {
			map.clear();
			map.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			map.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_PLAYTYPE_DOES_NOT_EXIST.getCode());
			map.put(Message.KEY_ERROR_MES, Message.Error.ERROR_PLAYTYPE_DOES_NOT_EXIST.getErrorMes());
		}
		return map;
	}
	//选择单式还是复式
	@Override
	public Map<String,Object> updateMulSinFlag(Integer id, Integer mulSinFlag) {
		Map<String,Object> map=new HashMap<String,Object>();
		boolean isNo=this.isNoPlayType(id);
		if(isNo) {
			playTypeDao.updateMulSinFlag(id, mulSinFlag);
			map.clear();
			map.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		}else {
			map.clear();
			map.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			map.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_PLAYTYPE_DOES_NOT_EXIST.getCode());
			map.put(Message.KEY_ERROR_MES, Message.Error.ERROR_PLAYTYPE_DOES_NOT_EXIST.getErrorMes());
		}
		return map;
	}
	//修改
	@Override
	public Map<String,Object> updatePlayType(PlayType playType) {
		Integer id=playType.getId();
		String classification=playType.getClassification();
		String ptName=playType.getPtName();
		String ptDesc=playType.getPtDesc();
		Integer state=playType.getState();
		Integer mulSinFlag=playType.getMulSinFlag();
		Integer isHidden=playType.getIsHidden();
		Map<String,Object> map=new HashMap<String,Object>();
		boolean isNo=this.isNoPlayType(id);
		if(isNo) {
			PlayType playTypeNew=this.queryById(id);
			if(!StringUtils.isBlank(classification)) {
				playTypeNew.setClassification(classification);
			}
			if(!StringUtils.isBlank(ptName)) {
				playTypeNew.setPtName(ptName);				
			}
			if(!StringUtils.isBlank(ptDesc)) {
				playTypeNew.setPtDesc(ptDesc);
			}
			if(state!=null) {
				playTypeNew.setState(state);
			}
			if(mulSinFlag!=null) {
				playTypeNew.setMulSinFlag(mulSinFlag);		
			}
			if(isHidden!=null) {
				playTypeNew.setIsHidden(isHidden);
			}
			playTypeDao.updatePlayType(playTypeNew);
			map.clear();
			map.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
		}else {
			map.clear();
			map.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			map.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_PLAYTYPE_DOES_NOT_EXIST.getCode());
			map.put(Message.KEY_ERROR_MES, Message.Error.ERROR_PLAYTYPE_DOES_NOT_EXIST.getErrorMes());
		}
		return map;
	}
	@Override
	public List<PlayType> queryByLotteryType(String lotteryType) {
		return playTypeDao.queryByLotteryType(lotteryType);
	}
	@Override
	public PlayType queryById(Integer id) {
		return playTypeDao.queryById(id);
	}
	//查询id是否存在
	@Override
	public boolean isNoPlayType(Integer id) {
		PlayType list=playTypeDao.queryById(id);
		if(list!=null) {
			return true;
		}
		return false;
	}
	//添加时的验证
	@Override
	public boolean isNoPlayType(PlayType playType) {
		List<PlayType> list=playTypeDao.queryByPlayType(playType);
		if(list!=null&&list.size()>0) {
			return true;
		}
		return false;
	}
	//修改排序
	@Override
	public Map<String, Object> updatePlayTypeSeq(String cacheCodeName, String allId) {
		Map<String,Object> map=new HashMap<String,Object>();
		String[] strArray = null;   
		strArray = allId.split(",");//把字符串转为String数组
		if(strArray.length>0) {
			for(int a=0;a<strArray.length;a++) {
				Integer id=Integer.valueOf(strArray[a]);
				PlayType playType=playTypeDao.queryById(id);
//				PlayType playType=null;
				List<PlayType> playTypeCacheLists=null;
				if(playType!=null) {
//					playType=list.get(0);
					playType.setSeq(a+1);
					playTypeDao.updatePlayTypeSeq(playType);
					/*playTypeCacheLists=cacheRedisService.getPlayType(cacheCodeName);
					if(playTypeCacheLists!=null&&playTypeCacheLists.size()>0) {
						Integer id1=null;
						for(int i=0; i<playTypeCacheLists.size();i++)    {   
						     PlayType playType1=playTypeCacheLists.get(i);
						     id1=playType1.getId();
						     if((int)id1==(int)id) {
						    	playType1.setSeq(a+1);
						    	playTypeCacheLists.set(i, playType1);
							}
						 }
						cacheRedisService.setPlayType(cacheCodeName, playTypeCacheLists);
					}*/
					List<PlayType> playTypes=playTypeDao.queryByLotteryType(cacheCodeName);
					cacheRedisService.setPlayType(cacheCodeName, playTypes);
				}
			}
			map.clear();
			map.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
			return map;
		}else {
			map.clear();
			map.put(Message.KEY_STATUS, Message.status.FAILED.getCode());
			map.put(Message.KEY_ERROR_CODE, Message.Error.ERROR_COMMON_ERROR_PARAMS.getCode());
			map.put(Message.KEY_ERROR_MES, Message.Error.ERROR_COMMON_ERROR_PARAMS.getErrorMes());
			return map;
		}
	}
	@Override
	public List<List<PlayTypeNum>> queryMainPs(String lotteryType) {
		String playType = Constants.PlayType.BDW_YW_SZ.getDesc();
		List<List<PlayTypeNum>> ret = new ArrayList<>();
		Map<String, PlayTypeNum> playTypeNumsMap = cacheRedisService.queryPlayTypeNum(lotteryType, playType);
		int indx = 0;
		for(int i = 0; i < 2; i++){
			List<PlayTypeNum> row = new ArrayList<>();
			ret.add(row);
			for(int ii = 0; ii < 5; ii++){
				PlayTypeNum playTypeNum = playTypeNumsMap.get(String.valueOf(indx));
				playTypeNum.setCurrentOdds(playTypeNum.getaOdds());
				row.add(playTypeNum);
				indx++;
			}
		}
		return ret;
	}
	@Override
	public List<List<PlayTypeNum>> queryMainPsHs(String lotteryType) {
		String codeTypeName = Constants.KEY_PLAY_TYPE_NUM;
		
		List<List<PlayTypeNum>> ret = new ArrayList<>();
		List<PlayTypeNum> row1 = new ArrayList<>();
		List<PlayTypeNum> row2 = new ArrayList<>();
		ret.add(row1);
		ret.add(row2);
		Map<String,Map<String,Map<String,PlayTypeNum>>> playTypeNums1 = cacheRedisService.queryPlayTypeNum(codeTypeName);
		playTypeNums1.entrySet().stream().filter(entry->entry.getKey().equals(lotteryType)).forEach(entry->{
			Map<String, Map<String, PlayTypeNum>> playTypePlayTypeMap = entry.getValue();
			playTypePlayTypeMap.entrySet().stream().filter(playTypeEntry->needIncluding(playTypeEntry.getKey())).forEach(playTypeEntry->{
				playTypeEntry.getValue().forEach((k,playTypeNum)->{
					StringBuffer buffer = new StringBuffer();
					String bettingNum = playTypeEntry.getKey().split("\\/")[1];					
					bettingNum = bettingNum.split("\\|")[1];
					buffer.append(bettingNum).append(playTypeNum.getBetNumDesc());
					playTypeNum.setDisplayName(buffer.toString());
					playTypeNum.setCurrentOdds(playTypeNum.getaOdds());
					if(row1.size() < 5){
						row1.add(playTypeNum);
					}else{
						row2.add(playTypeNum);
					}
				});
			});			
		});
		
		return ret;
	}
	private boolean needIncluding(String key) {
		String playType1 = Constants.PlayType.EWHS_BS_DS.getDesc();
		String playType2 = Constants.PlayType.EWHS_BG_DS.getDesc();
		String playType3 = Constants.PlayType.EWHS_SG_DS.getDesc();
		String playType4 = Constants.PlayType.SWHS_DX.getDesc();
		String playType5 = Constants.PlayType.SWHS_DS.getDesc();
		
		if(key.equals(playType1)
				|| key.equals(playType2)
				|| key.equals(playType3)
				|| key.equals(playType4)
				|| key.equals(playType5)){
			return true;
		}
		return false;
	}
	
	private boolean isYwdw(String key) {
		String ywdw = Constants.PlayType.YWDW_BW_DS.getName().split("_")[0];
		String dx = Constants.PlayType.YWDW_BW_DX.getName().split("_")[2];
		String zh = Constants.PlayType.YWDW_BW_ZH.getName().split("_")[2];
		String ds = Constants.PlayType.YWDW_BW_DS.getName().split("_")[2];
		if(key.contains(ywdw)
				&& (key.contains(dx) || key.contains(zh) || key.contains(ds))){
			return true;
		}
		return false;
	}
	
	private boolean isZx3(String key) {
		String zx = Constants.PlayType.ZX_ZX3_5M.getName().split("_")[0];
		String zx3 = Constants.PlayType.ZX_ZX3_5M.getName().split("_")[1];
		if(key.contains(zx)
				&& (key.contains(zx3))){
			return true;
		}
		return false;
	}
	
	
	@Override
	public List<BitColumn> queryMainPsDwd(String lotteryType) {
		String codeTypeName = Constants.KEY_PLAY_TYPE_NUM;
		
		List<BitColumn> ret = new ArrayList<>();
		initBitColumn(ret);
		Map<String,Map<String,Map<String,PlayTypeNum>>> playTypeNums1 = cacheRedisService.queryPlayTypeNum(codeTypeName);
		playTypeNums1.entrySet().stream().filter(entry->entry.getKey().equals(lotteryType)).forEach(entry->{
			Map<String, Map<String, PlayTypeNum>> playTypePlayTypeMap = entry.getValue();
			playTypePlayTypeMap.entrySet().stream().filter(playTypeEntry->isYwdw(playTypeEntry.getKey())).forEach(playTypeEntry->{
				System.out.println(playTypeEntry.getKey());
				playTypeEntry.getValue().forEach((k,playTypeNum)->{
					System.out.println(k);
					playTypeNum.setCurrentOdds(playTypeNum.getaOdds());
					if(isYwdwBw(playTypeEntry.getKey())){
						BitColumn column = null;
						if(ret.size() < 3){
							
						}else{
							column = ret.get(0);
						}
						List<PlayTypeNum> playTypeNums = column.getPlayTypeNums();
						playTypeNums.add(playTypeNum);
					}
					
					if(isYwdwSw(playTypeEntry.getKey())){
						BitColumn column = null;
						if(ret.size() < 2){
							
						}else{
							column = ret.get(1);
						}
						List<PlayTypeNum> playTypeNums = column.getPlayTypeNums();
						playTypeNums.add(playTypeNum);
					}
					
					if(isYwdwGw(playTypeEntry.getKey())){
						BitColumn column = null;
						if(ret.size() < 1){
							
						}else{
							column = ret.get(2);
						}
						List<PlayTypeNum> playTypeNums = column.getPlayTypeNums();
						playTypeNums.add(playTypeNum);
					}
				});
			});			
		});
		
		return ret;
	}
	private void initBitColumn(List<BitColumn> ret) {
		String gwName = Constants.PlayType.YWDW_GW_DX.getDesc().split("\\/")[1].split("\\|")[1];
		String swName = Constants.PlayType.YWDW_SW_DX.getDesc().split("\\/")[1].split("\\|")[1];
		String bwName = Constants.PlayType.YWDW_BW_DS.getDesc().split("\\/")[1].split("\\|")[1];
		
		BitColumn column = new BitColumn();
		column.setColumnName(bwName);
		column.setBitIndex(0);
		column.setPlayTypeNums(new ArrayList<>());
		ret.add(column);
		
		column = new BitColumn();
		column.setColumnName(swName);
		column.setBitIndex(1);
		column.setPlayTypeNums(new ArrayList<>());
		ret.add(column);
		
		column = new BitColumn();
		column.setColumnName(gwName);
		column.setBitIndex(2);
		column.setPlayTypeNums(new ArrayList<>());
		ret.add(column);
	}
	
	private void initZsBitColumn(List<BitColumn> ret) {
		String zx35m = Constants.PlayType.ZX_ZX3_5M.getDesc().split("\\/")[0].split("\\|")[1];
		String zx36m = Constants.PlayType.ZX_ZX3_6M.getDesc().split("\\/")[0].split("\\|")[1];
		String zx37m = Constants.PlayType.ZX_ZX3_7M.getDesc().split("\\/")[0].split("\\|")[1];
		String zx38m = Constants.PlayType.ZX_ZX3_8M.getDesc().split("\\/")[0].split("\\|")[1];
		String zx39m = Constants.PlayType.ZX_ZX3_9M.getDesc().split("\\/")[0].split("\\|")[1];
		String zx3qb = Constants.PlayType.ZX_ZX3_QB.getDesc().split("\\/")[0].split("\\|")[1];
		
		BitColumn column = new BitColumn();
		column.setColumnName(zx35m);
		column.setBitIndex(0);
		column.setPlayTypeNums(new ArrayList<>());
		ret.add(column);
		
		column = new BitColumn();
		column.setColumnName(zx36m);
		column.setBitIndex(1);
		column.setPlayTypeNums(new ArrayList<>());
		ret.add(column);
		
		column = new BitColumn();
		column.setColumnName(zx37m);
		column.setBitIndex(2);
		column.setPlayTypeNums(new ArrayList<>());
		ret.add(column);
		
		column = new BitColumn();
		column.setColumnName(zx38m);
		column.setBitIndex(3);
		column.setPlayTypeNums(new ArrayList<>());
		ret.add(column);
		
		column = new BitColumn();
		column.setColumnName(zx39m);
		column.setBitIndex(4);
		column.setPlayTypeNums(new ArrayList<>());
		ret.add(column);
		
		column = new BitColumn();
		column.setColumnName(zx3qb);
		column.setBitIndex(5);
		column.setPlayTypeNums(new ArrayList<>());
		ret.add(column);
	}
	
	
	private boolean isYwdwGw(String key) {
		String playType1 = Constants.PlayType.YWDW_GW_DX.getName().split("_")[1];
		
		if(key.contains(playType1)){
			return true;
		}
		return false;
	}
	private boolean isYwdwSw(String key) {
		String playType1 = Constants.PlayType.YWDW_SW_DX.getName().split("_")[1];
		
		if(key.contains(playType1)){
			return true;
		}
		return false;
	}
	private boolean isYwdwBw(String key) {
		String playType1 = Constants.PlayType.YWDW_BW_DS.getName().split("_")[1];
		
		if(key.contains(playType1)){
			return true;
		}
		return false;
	}
	@Override
	public List<List<PlayTypeNum>> queryBwsz(String lotteryType) {
		String playType = Constants.PlayType.YWDW_BW_SZ.getDesc();
		List<List<PlayTypeNum>> ret = new ArrayList<>();
		Map<String, PlayTypeNum> playTypeNumsMap = cacheRedisService.queryPlayTypeNum(lotteryType, playType);
		int indx = 0;
		for(int i = 0; i < 2; i++){
			List<PlayTypeNum> row = new ArrayList<>();
			ret.add(row);
			for(int ii = 0; ii < 5; ii++){
				PlayTypeNum playTypeNum = playTypeNumsMap.get(String.valueOf(indx));
				playTypeNum.setCurrentOdds(playTypeNum.getaOdds());				
				playTypeNum.setDisplayName(playTypeNum.getBetNumDesc());
				
				row.add(playTypeNum);
				indx++;
			}
		}
		return ret;
	}
	@Override
	public List<List<PlayTypeNum>> querySwsz(String lotteryType) {
		String playType = Constants.PlayType.YWDW_SW_SZ.getDesc();
		List<List<PlayTypeNum>> ret = new ArrayList<>();
		Map<String, PlayTypeNum> playTypeNumsMap = cacheRedisService.queryPlayTypeNum(lotteryType, playType);
		int indx = 0;
		for(int i = 0; i < 2; i++){
			List<PlayTypeNum> row = new ArrayList<>();
			ret.add(row);
			for(int ii = 0; ii < 5; ii++){
				PlayTypeNum playTypeNum = playTypeNumsMap.get(String.valueOf(indx));
				playTypeNum.setCurrentOdds(playTypeNum.getaOdds());
				playTypeNum.setDisplayName(playTypeNum.getBetNumDesc());
				row.add(playTypeNum);
				indx++;
			}
		}
		return ret;
	}
	@Override
	public List<List<PlayTypeNum>> queryGwsz(String lotteryType) {
		String playType = Constants.PlayType.YWDW_GW_SZ.getDesc();
		List<List<PlayTypeNum>> ret = new ArrayList<>();
		Map<String, PlayTypeNum> playTypeNumsMap = cacheRedisService.queryPlayTypeNum(lotteryType, playType);
		int indx = 0;
		for(int i = 0; i < 2; i++){
			List<PlayTypeNum> row = new ArrayList<>();
			ret.add(row);
			for(int ii = 0; ii < 5; ii++){
				PlayTypeNum playTypeNum = playTypeNumsMap.get(String.valueOf(indx));
				playTypeNum.setCurrentOdds(playTypeNum.getaOdds());
				playTypeNum.setDisplayName(playTypeNum.getBetNumDesc());
				row.add(playTypeNum);
				indx++;
			}
		}
		return ret;
	}
	@Override
	public List<List<PlayTypeNum>> queryEzdw(String lotteryType, String numType) {
		String playType = parseEzdwPlayTypeFromNumType(numType == null?"0":numType.trim());
		List<List<PlayTypeNum>> ret = new ArrayList<>();
		Map<String, PlayTypeNum> playTypeNumsMap = cacheRedisService.queryPlayTypeNum(lotteryType, playType);
		Map<String, PlayTypeNum> playTypeNumsMap_ = new TreeMap<>(); 
		playTypeNumsMap_.putAll(playTypeNumsMap);
		
		int indx = 0;
		Iterator<Entry<String, PlayTypeNum>> playTypeNumsIte = playTypeNumsMap_.entrySet().iterator();
		List<PlayTypeNum> row = null;
		while(playTypeNumsIte.hasNext()){			
			if(indx % 5 == 0){
				row = new ArrayList<>();
				ret.add(row);
			}
			
			row.add(playTypeNumsIte.next().getValue());
			
			indx++;
		}
		
		return ret;
	}
	
	private String parseEzdwPlayTypeFromNumType(String numType){
		if(Constants.EzdwNumType.EWDW_BS_SZ.getCode() == Integer.parseInt(numType)){
			return Constants.EzdwNumType.EWDW_BS_SZ.getDesc();
		}
		if(Constants.EzdwNumType.EWDW_BG_SZ.getCode() == Integer.parseInt(numType)){
			return Constants.EzdwNumType.EWDW_BG_SZ.getDesc();
		}
		
		if(Constants.EzdwNumType.EWDW_SG_SZ.getCode() == Integer.parseInt(numType)){
			return Constants.EzdwNumType.EWDW_SG_SZ.getDesc();
		}
		return Constants.EzdwNumType.EWDW_BS_SZ.getDesc();
	}
	
	private String parseEzhsPlayTypeFromNumType(String numType){
		if(Constants.EzhsNumType.EWHS_BS_SZ.getCode() == Integer.parseInt(numType)){
			return Constants.EzhsNumType.EWHS_BS_SZ.getDesc();
		}
		if(Constants.EzhsNumType.EWHS_BG_SZ.getCode() == Integer.parseInt(numType)){
			return Constants.EzhsNumType.EWHS_BG_SZ.getDesc();
		}
		
		if(Constants.EzhsNumType.EWHS_SG_SZ.getCode() == Integer.parseInt(numType)){
			return Constants.EzhsNumType.EWHS_SG_SZ.getDesc();
		}
		return Constants.EzhsNumType.EWHS_BS_SZ.getDesc();
	}
	
	@Override
	public List<List<PlayTypeNum>> querySzdw(String lotteryType) {
		String playType = Constants.PlayType.SWDW_SZ.getDesc();
		List<List<PlayTypeNum>> ret = new ArrayList<>();
		Map<String, PlayTypeNum> playTypeNumsMap = cacheRedisService.queryPlayTypeNum(lotteryType, playType);
		Map<String, PlayTypeNum> playTypeNumsMap_ = new TreeMap<>(); 
		playTypeNumsMap_.putAll(playTypeNumsMap);
		
		int indx = 0;
		Iterator<Entry<String, PlayTypeNum>> playTypeNumsIte = playTypeNumsMap_.entrySet().iterator();
		List<PlayTypeNum> row = null;
		while(playTypeNumsIte.hasNext()){			
			if(indx % 5 == 0){
				row = new ArrayList<>();
				ret.add(row);
			}
			
			row.add(playTypeNumsIte.next().getValue());
			
			indx++;
		}
		
		return ret;
	}
	@Override
	public List<List<PlayTypeNum>> queryEzzh(String lotteryType) {
		String playType = Constants.PlayType.BDW_EW_SZ.getDesc();
		List<List<PlayTypeNum>> ret = new ArrayList<>();
		Map<String, PlayTypeNum> playTypeNumsMap = cacheRedisService.queryPlayTypeNum(lotteryType, playType);
		Map<String, PlayTypeNum> playTypeNumsMap_ = new TreeMap<>(); 
		playTypeNumsMap_.putAll(playTypeNumsMap);
		
		int indx = 0;
		Iterator<Entry<String, PlayTypeNum>> playTypeNumsIte = playTypeNumsMap_.entrySet().iterator();
		List<PlayTypeNum> row = null;
		while(playTypeNumsIte.hasNext()){			
			if(indx % 5 == 0){
				row = new ArrayList<>();
				ret.add(row);
			}
			
			row.add(playTypeNumsIte.next().getValue());
			
			indx++;
		}
		
		return ret;
	}
	@Override
	public List<List<PlayTypeNum>> queryEzhs(String lotteryType, String numType) {
		String playType = parseEzhsPlayTypeFromNumType(numType == null?"0":numType.trim());
		List<List<PlayTypeNum>> ret = new ArrayList<>();
		Map<String, PlayTypeNum> playTypeNumsMap = cacheRedisService.queryPlayTypeNum(lotteryType, playType);
		Map<String, PlayTypeNum> playTypeNumsMap_ = new TreeMap<>(); 
		playTypeNumsMap_.putAll(playTypeNumsMap);
		
		int indx = 0;
		Iterator<Entry<String, PlayTypeNum>> playTypeNumsIte = playTypeNumsMap_.entrySet().iterator();
		List<PlayTypeNum> row = null;
		while(playTypeNumsIte.hasNext()){			
			if(indx % 5 == 0){
				row = new ArrayList<>();
				ret.add(row);
			}
			
			row.add(playTypeNumsIte.next().getValue());
			
			indx++;
		}
		
		return ret;
	}
	@Override
	public List<List<PlayTypeNum>> querySzhs(String lotteryType) {
		String playType = Constants.PlayType.SWHS_SZ.getDesc();
		List<List<PlayTypeNum>> ret = new ArrayList<>();
		Map<String, PlayTypeNum> playTypeNumsMap = cacheRedisService.queryPlayTypeNum(lotteryType, playType);
		Map<String, PlayTypeNum> playTypeNumsMap_ = new TreeMap<>(); 
		playTypeNumsMap_.putAll(playTypeNumsMap);
		
		int indx = 0;
		Iterator<Entry<String, PlayTypeNum>> playTypeNumsIte = playTypeNumsMap_.entrySet().iterator();
		List<PlayTypeNum> row = null;
		while(playTypeNumsIte.hasNext()){			
			if(indx % 5 == 0){
				row = new ArrayList<>();
				ret.add(row);
			}
			
			row.add(playTypeNumsIte.next().getValue());
			
			indx++;
		}
		
		return ret;
	}
	@Override
	public List<List<PlayTypeNum>> querySzzh(String lotteryType) {
		String playType = Constants.PlayType.BDW_SW_SZ.getDesc();
		List<List<PlayTypeNum>> ret = new ArrayList<>();
		Map<String, PlayTypeNum> playTypeNumsMap = cacheRedisService.queryPlayTypeNum(lotteryType, playType);
		Map<String, PlayTypeNum> playTypeNumsMap_ = new TreeMap<>(); 
		playTypeNumsMap_.putAll(playTypeNumsMap);
		
		int indx = 0;
		Iterator<Entry<String, PlayTypeNum>> playTypeNumsIte = playTypeNumsMap_.entrySet().iterator();
		List<PlayTypeNum> row = null;
		while(playTypeNumsIte.hasNext()){			
			if(indx % 5 == 0){
				row = new ArrayList<>();
				ret.add(row);
			}
			
			row.add(playTypeNumsIte.next().getValue());
			
			indx++;
		}
		
		return ret;
	}
	
	@Override
	public List<BitColumn> queryZx3(String lotteryType) {
		String codeTypeName = Constants.KEY_PLAY_TYPE_NUM;
		
		List<BitColumn> ret = new ArrayList<>();
		initZsBitColumn(ret);
		Map<String,Map<String,Map<String,PlayTypeNum>>> playTypeNums1 = cacheRedisService.queryPlayTypeNum(codeTypeName);
		playTypeNums1.entrySet().stream().filter(entry->entry.getKey().equals(lotteryType)).forEach(entry->{
			Map<String, Map<String, PlayTypeNum>> playTypePlayTypeMap = entry.getValue();
			playTypePlayTypeMap.entrySet().stream().filter(playTypeEntry->isZx3(playTypeEntry.getKey())).forEach(playTypeEntry->{
				System.out.println(playTypeEntry.getKey());
				playTypeEntry.getValue().forEach((k,playTypeNum)->{
					System.out.println(k);
					playTypeNum.setCurrentOdds(playTypeNum.getaOdds());
					if(isZx35m(playTypeEntry.getKey())){
						BitColumn column = ret.get(0);
						List<PlayTypeNum> playTypeNums = column.getPlayTypeNums();
						playTypeNums.add(playTypeNum);
					}
					
					if(isZx36m(playTypeEntry.getKey())){
						BitColumn column = ret.get(1);
						List<PlayTypeNum> playTypeNums = column.getPlayTypeNums();
						playTypeNums.add(playTypeNum);
					}
					
					if(isZx37m(playTypeEntry.getKey())){
						BitColumn column = ret.get(2);
						List<PlayTypeNum> playTypeNums = column.getPlayTypeNums();
						playTypeNums.add(playTypeNum);
					}
					
					if(isZx38m(playTypeEntry.getKey())){
						BitColumn column = ret.get(3);
						List<PlayTypeNum> playTypeNums = column.getPlayTypeNums();
						playTypeNums.add(playTypeNum);
					}
					
					if(isZx39m(playTypeEntry.getKey())){
						BitColumn column = ret.get(4);
						List<PlayTypeNum> playTypeNums = column.getPlayTypeNums();
						playTypeNums.add(playTypeNum);
					}
					
					if(isZx3Qb(playTypeEntry.getKey())){
						BitColumn column = ret.get(5);
						List<PlayTypeNum> playTypeNums = column.getPlayTypeNums();
						playTypeNums.add(playTypeNum);
					}
				});
			});			
		});
		
		return ret;
	}
	
	/**
	 * zu xuan 3 5 ma
	 * @param key
	 * @return
	 */
	private boolean isZx35m(String key) {
		String playType1 = Constants.PlayType.ZX_ZX3_5M.getName().split("_")[2];
		
		if(key.contains(playType1)){
			return true;
		}
		return false;
	}
	
	/**
	 * zu xuan 3 6 ma
	 * @param key
	 * @return
	 */
	private boolean isZx36m(String key) {
		String playType1 = Constants.PlayType.ZX_ZX3_6M.getName().split("_")[2];
		
		if(key.contains(playType1)){
			return true;
		}
		return false;
	}
	
	/**
	 * zu xuan 3 7 ma
	 * @param key
	 * @return
	 */
	private boolean isZx37m(String key) {
		String playType1 = Constants.PlayType.ZX_ZX3_7M.getName().split("_")[2];
		
		if(key.contains(playType1)){
			return true;
		}
		return false;
	}
	
	/**
	 * zu xuan 3 8 ma
	 * @param key
	 * @return
	 */
	private boolean isZx38m(String key) {
		String playType1 = Constants.PlayType.ZX_ZX3_8M.getName().split("_")[2];
		
		if(key.contains(playType1)){
			return true;
		}
		return false;
	}
	
	/**
	 * zu xuan 3 9 ma
	 * @param key
	 * @return
	 */
	private boolean isZx39m(String key) {
		String playType1 = Constants.PlayType.ZX_ZX3_9M.getName().split("_")[2];
		
		if(key.contains(playType1)){
			return true;
		}
		return false;
	}
	
	/**
	 * zu xuan 3 10 ma
	 * @param key
	 * @return
	 */
	private boolean isZx3Qb(String key) {
		String playType1 = Constants.PlayType.ZX_ZX3_QB.getName().split("_")[2];
		
		if(key.contains(playType1)){
			return true;
		}
		return false;
	}
}

