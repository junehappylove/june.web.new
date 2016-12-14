package com.june.dto.front.checkdetail;

import com.june.common.PageDTO;

public class CommentDto extends PageDTO<CommentDto>{
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 170704439390969243L;
	private String commentId;//评论的id
	private String commentContent;//评论的内容
	private String commentDate;//评论的日期
	private String contentId;//评论的文章id
	private String commentUser;//评论用户id
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getCommentUser() {
		return commentUser;
	}
	public void setCommentUser(String commentUser) {
		this.commentUser = commentUser;
	}
	@Override
	protected String getDtoName() {
		return "评论";
	}
	
}
