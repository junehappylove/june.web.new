package com.june.service.back.archive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.dao.back.archive.ArchiveDao;
import com.june.dto.back.archive.ArchiveDto;

@Service
public class ArchiveService extends BaseService<ArchiveDao,ArchiveDto> {

	@Autowired
	private ArchiveDao archiveDao;

	public ArchiveDto getList(ArchiveDto archiveDto) {
		List<ArchiveDto> list = archiveDao.getPageList(archiveDto);
		int total = archiveDao.getTotal(archiveDto);
		archiveDto.setRows(list);
		archiveDto.setTotal(total);
		return archiveDto;
	}

	public void saveArchiveInfo(ArchiveDto archiveDto) {
		archiveDao.saveArchiveInfo(archiveDto);
	}

	public void saveArchivePic(ArchiveDto archiveDto) {
		archiveDao.saveArchivePic(archiveDto);
	}

	public List<ArchiveDto> getPicList(String id) {
		List<ArchiveDto> list = archiveDao.getPicList(id);
		return list;
	}
}
