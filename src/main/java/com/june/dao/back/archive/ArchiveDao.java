package com.june.dao.back.archive;

import java.util.List;

import com.june.common.BaseDao;
import com.june.dto.back.archive.ArchiveDto;

public interface ArchiveDao extends BaseDao<ArchiveDto>{

	public void saveArchiveInfo(ArchiveDto archiveDto);
	public void saveArchivePic(ArchiveDto archiveDto);
	public List<ArchiveDto> getPicList(String id);
}
