package com.bit.UntitledBistro.service.seobis;

import java.util.HashMap;
import java.util.List;

import com.bit.UntitledBistro.model.seobis.Seobis_MemberDTO;

public interface Seobis_MemberService {
	public List<Seobis_MemberDTO> Seobis_MemberList(HashMap<String, Object> map);   //회원 리스트
	public void Seobis_MemberInsert(Seobis_MemberDTO seobis_MemberDTO_dto);   //회원 추가
	public void Seobis_MemberDelete(Seobis_MemberDTO seobis_MemberDTO_dto); //회원 삭제
	public void Seobis_MemberUpdate(Seobis_MemberDTO seobis_MemberDTO_dto); //회원 수정
	public Seobis_MemberDTO Seobis_MemberSelect(String member_id); // 회원 읽기
	
	/*
	 * public Seobis_ReserveDTO Seobis_ReserveCalendar(String member_id); // 캘린더 예약
	 * public List<Seobis_ReserveDTO> Seobis_ReserveCalendarList(String member_id);
	 * //캘린더 리스트
	 */}