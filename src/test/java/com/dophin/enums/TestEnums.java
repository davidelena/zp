package com.dophin.enums;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestEnums
{

	@Test
	public void testGetComputerSkillEnums()
	{
		ComputerSkillEnum[] skills = ComputerSkillEnum.values();
		for (ComputerSkillEnum item : skills)
		{
			System.out.println(String.format("%s: %s", item.getParent(), item.getDesc()));
		}
	}

}
