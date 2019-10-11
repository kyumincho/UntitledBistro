package com.bit.UntitledBistro.service.jaego;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bit.UntitledBistro.model.jaego.ChangeItemDTO;
import com.bit.UntitledBistro.model.jaego.Condition;
import com.bit.UntitledBistro.model.jaego.DefectItemDTO;
import com.bit.UntitledBistro.model.jaego.InItemDTO;
import com.bit.UntitledBistro.model.jaego.ItemDTO;
import com.bit.UntitledBistro.model.jaego.JaegoDAOImpl;
import com.bit.UntitledBistro.model.jaego.OutItemDTO;
import com.bit.UntitledBistro.model.jaego.ProductDTO;
import com.bit.UntitledBistro.model.jaego.RiskItemDTO;
import com.bit.UntitledBistro.model.jaego.SafeItemDTO;

@Service
@Transactional
public class JaegoServiceImpl {
	
	@Autowired
	private JaegoDAOImpl dao;
	
	// 재고 테이블 전체조회
	public List<ItemDTO> itemSelectList(Condition condition) {
		return dao.itemSelectList(condition);
	}
	
	// 입고 테이블 전체조회
	public List<InItemDTO> inItemSelectList(Condition condition) {
		return dao.inItemSelectList(condition);
	}
	
	// 출고 테이블 전체조회
	public List<OutItemDTO> outItemSelectList(Condition condition) {
		return dao.outItemSelectList(condition);
	}
	
	// 재고변동표 전체조회
	public List<ChangeItemDTO> changeItemSelectList(Condition condition) {
		return dao.changeItemSelectList(condition);
	}
	
	// 불량 테이블 다중등록
	public int defectItemInserts(DefectItemDTO[] defectItemDTOs) {
		for(DefectItemDTO defectItemDTO : defectItemDTOs) {
			dao.defectItemInsert(defectItemDTO);
			
			OutItemDTO outItemDTO = new OutItemDTO();
			outItemDTO.setOi_product_code(defectItemDTO.getDi_product_code());
			outItemDTO.setOi_qty(defectItemDTO.getDi_qty());
			dao.outItemInsert(outItemDTO);
			
			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setItem_product_code(defectItemDTO.getDi_product_code());
			itemDTO.setItem_qty(defectItemDTO.getDi_qty());
			dao.itemMinusUpdate(itemDTO);
			
		} // for end
		
		List<SafeItemDTO> safeItemList = dao.safeItemSelectList();
		return dao.riskItemCount(safeItemList);
	}
	
	// 불량 테이블 전체조회
	public List<DefectItemDTO> defectItemSelectList(Condition condition) {
		return dao.defectItemSelectList(condition);
	}
	
	// 불량 테이블 다중수정
	public void defectItemUpdates(DefectItemDTO[] defectItemDTOs) {
		List<DefectItemDTO> defectItemList = Arrays.asList(defectItemDTOs);
		dao.defectItemUpdates(defectItemList);
	}
	
	// 불량 테이블 다중삭제
	public void defectItemDeletes(DefectItemDTO[] defectItemDTOs) {
		List<DefectItemDTO> defectItemList = Arrays.asList(defectItemDTOs);
		dao.defectItemDeletes(defectItemList);
	}
	
	// 품목 테이블 전체조회
	public List<ProductDTO> productSelectList(Condition condition) {
		return dao.productSelectList(condition);
	}
	
	// 위험재고 갯수조회
	public int riskItemCount() {
		List<SafeItemDTO> safeItemList = dao.safeItemSelectList();
		return dao.riskItemCount(safeItemList);
	}
	
	// 위험재고 전체조회
	public List<RiskItemDTO> riskItemSelectList() {
		List<SafeItemDTO> safeItemList = dao.safeItemSelectList();
		return dao.riskItemSelectList(safeItemList);
	}
	
	// 안전 테이블 전체조회
	public List<SafeItemDTO> safeItemSelectList() {
		return dao.safeItemSelectList();
	}
	
	// 안전 테이블 다중수정
	public void safeItemUpdates(SafeItemDTO[] safeItemDTOs) {
		List<SafeItemDTO> safeItemList = Arrays.asList(safeItemDTOs);
		dao.safeItemUpdates(safeItemList);
	}
	
	// 안전 테이블 다중삭제
	public void safeItemDeletes(SafeItemDTO[] safeItemDTOs) {
		List<SafeItemDTO> safeItemList = Arrays.asList(safeItemDTOs);
		dao.safeItemDeletes(safeItemList);
	}
	
	// 안전 테이블 다중등록
	public int safeItemInserts(SafeItemDTO[] safeItemDTOs) {
		List<SafeItemDTO> list = Arrays.asList(safeItemDTOs);
		int result = dao.safeItemValidate(list);
		
		if(result == 0) {
			dao.safeItemInserts(list);
			List<SafeItemDTO> listAll = dao.safeItemSelectList();
			return dao.riskItemCount(listAll);
		} else {
			return -1;
		}
		
	}
	
	// 입고 테이블 등록
	public Map<String, Object> inItemInsert(InItemDTO inItemDTO) {
		dao.inItemInsert(inItemDTO);
		
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItem_product_code(inItemDTO.getIi_product_code());
		itemDTO.setItem_qty(inItemDTO.getIi_qty());
		
		int result = dao.itemValidate(itemDTO);
		if(result == 0) {
			dao.itemInsert(itemDTO);
		} else {
			dao.itemPlusUpdate(itemDTO);
		}
		
		List<SafeItemDTO> safeItemList = dao.safeItemSelectList();
		int count = dao.riskItemCount(safeItemList);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		map.put("count", count);
		return map;
	}
	
	// 출고 테이블 등록
	public int outItemInsert(OutItemDTO outItemDTO) {
		dao.outItemInsert(outItemDTO);
		
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItem_product_code(outItemDTO.getOi_product_code());
		itemDTO.setItem_qty(outItemDTO.getOi_qty());
		dao.itemMinusUpdate(itemDTO);

		List<SafeItemDTO> safeItemList = dao.safeItemSelectList();
		return dao.riskItemCount(safeItemList);
	}
	
}
