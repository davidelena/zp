package com.dophin.service;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dophin.dao.RecruitInfoDAO;

public class TestPaginateNum
{
	@Autowired
	private RecruitInfoDAO recruitInfoDAO;

	@Test
	public void testPn()
	{
		int size = 28;
		generateInitPnHtml(size);
		System.err.println("===============================================");
		generateCurrentPnHtml(7, 7);
	}

	private String generateInitPnHtml(int size)
	{
		int pageIndexNum = (size % 5 == 0) ? (size / 5) : (size / 5) + 1;
		int pageRemainNum = size % 5;
		System.err.println(pageIndexNum + "页");
		System.err.println(pageRemainNum + "条");

		StringBuilder sb = new StringBuilder("首页,上一页");
		for (int i = 1; i <= pageIndexNum; i++)
		{
			sb.append(String.valueOf(i));
			if (i >= 5)
			{
				break;
			}
		}

		sb.append("下一页,尾页");
		System.err.println(sb.toString());
		return sb.toString();
	}

	private String generateCurrentPnHtml(int pageIndexNum, int pn)
	{
		StringBuilder sb = new StringBuilder("首页,上一页");
		if (pageIndexNum <= 5)
		{
			for (int i = 1; i <= pageIndexNum; i++)
			{
				if (i == pn)
				{
					sb.append(String.format("<%d>", i));
				} else
				{
					sb.append(String.valueOf(i));
				}
			}
		} else
		{
			if (pn > 5)
			{
				for (int i = (pn - 5) + 1; i <= pn; i++)
				{
					if (i == pn)
					{
						sb.append(String.format("<%d>", i));
					} else
					{
						sb.append(String.valueOf(i));
					}
				}
			} else
			{
				for (int i = 1; i <= 5; i++)
				{
					if (i == pn)
					{
						sb.append(String.format("<%d>", i));
					} else
					{
						sb.append(String.valueOf(i));
					}
				}
			}
		}

		sb.append("下一页,尾页");
		System.err.println(sb.toString());
		return sb.toString();
	}

}
