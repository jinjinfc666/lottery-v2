package com.jll.game.playtypefacade;

import java.util.ArrayList;
import java.util.List;

import com.jll.game.playtype.PlayTypeFacade;
import com.jll.game.playtypefacade.tc3.BdwEwPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.BdwQyPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.BdwSwPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.BwDsPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.BwDxPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.BwSZPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.BwZhPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.EwDwBgSZPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.EwDwBsSZPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.EwDwSgSZPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.EwhsBgDsPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.EwhsBgSzPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.EwhsBsDsPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.EwhsBsSzPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.EwhsSgDsPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.EwhsSgSzPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.GwDsPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.GwDxPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.GwSZPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.GwZhPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.SwDsPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.SwDwSZPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.SwDxPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.SwSZPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.SwZhPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.SwhsDsPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.SwhsDxPlayTypeFacadeImpl;
import com.jll.game.playtypefacade.tc3.SwhsSzPlayTypeFacadeImpl;

public class PlayTypeFactory {

	private static PlayTypeFactory factory;
	
	
	private List<PlayTypeFacade> playTypeFacades = new ArrayList<>();
	
	private PlayTypeFactory() {
		QszxPlayTypeFacadeImpl qszxPlayTypeFacadeImpl = new QszxPlayTypeFacadeImpl();
		QszxDsPlayTypeFacadeImpl qszxDsPlayTypeFacadeImpl = new QszxDsPlayTypeFacadeImpl();
		ZszxPlayTypeFacadeImpl zszxPlayTypeFacadeImpl = new ZszxPlayTypeFacadeImpl();
		ZszxDsPlayTypeFacadeImpl zszxDsPlayTypeFacadeImpl = new ZszxDsPlayTypeFacadeImpl();
		HszxPlayTypeFacadeImpl hszxPlayTypeFacadeImpl = new HszxPlayTypeFacadeImpl();
		HszxDsPlayTypeFacadeImpl hszxDsPlayTypeFacadeImpl = new HszxDsPlayTypeFacadeImpl();
		QszuxZsPlayTypeFacadeImpl qszuxZsPlayTypeFacadeImpl = new QszuxZsPlayTypeFacadeImpl();
		QszuxZlPlayTypeFacadeImpl qszuxZlPlayTypeFacadeImpl = new QszuxZlPlayTypeFacadeImpl();
		QszuxMixPlayTypeFacadeImpl qszuxMixPlayTypeFacadeImpl = new QszuxMixPlayTypeFacadeImpl();
		ZszuxZsPlayTypeFacadeImpl zszuxZsPlayTypeFacadeImpl = new ZszuxZsPlayTypeFacadeImpl();
		ZszuxZlPlayTypeFacadeImpl zszuxZlPlayTypeFacadeImpl = new ZszuxZlPlayTypeFacadeImpl();
		ZszuxHhzxPlayTypeFacadeImpl zszuxHhzxPlayTypeFacadeImpl = new ZszuxHhzxPlayTypeFacadeImpl();
		HszuxZsPlayTypeFacadeImpl hszuxZsPlayTypeFacadeImpl = new HszuxZsPlayTypeFacadeImpl();
		HszuxZlPlayTypeFacadeImpl hszuxZlPlayTypeFacadeImpl = new HszuxZlPlayTypeFacadeImpl();
		HszuxHhzxPlayTypeFacadeImpl hszuxHhzxPlayTypeFacadeImpl = new HszuxHhzxPlayTypeFacadeImpl();
		Wxq2PlayTypeFacadeImpl wxq2PlayTypeFacadeImpl = new Wxq2PlayTypeFacadeImpl();
		Wxq2DsPlayTypeFacadeImpl wxq2DsPlayTypeFacadeImpl = new Wxq2DsPlayTypeFacadeImpl();
		Wxq2ZxPlayTypeFacadeImpl wxq2ZxPlayTypeFacadeImpl = new Wxq2ZxPlayTypeFacadeImpl();
		Wxh2PlayTypeFacadeImpl wxh2PlayTypeFacadeImpl = new Wxh2PlayTypeFacadeImpl();
		Wxh2DsPlayTypeFacadeImpl wxh2DsPlayTypeFacadeImpl = new Wxh2DsPlayTypeFacadeImpl();
		Wxh2ZxPlayTypeFacadeImpl wxh2ZxPlayTypeFacadeImpl = new Wxh2ZxPlayTypeFacadeImpl();
		BdwQsPlayTypeFacadeImpl bdwQsPlayTypeFacadeImpl = new BdwQsPlayTypeFacadeImpl();
		BdwZsPlayTypeFacadeImpl bdwZsPlayTypeFacadeImpl = new BdwZsPlayTypeFacadeImpl();
		BdwHsPlayTypeFacadeImpl bdwHsPlayTypeFacadeImpl = new BdwHsPlayTypeFacadeImpl();
		DwdPlayTypeFacadeImpl dwdPlayTypeFacadeImpl = new DwdPlayTypeFacadeImpl();
		
		Ds1PlayTypeFacadeImpl ds1PlayTypeFacadeImpl = new Ds1PlayTypeFacadeImpl();
		Ds2PlayTypeFacadeImpl ds2PlayTypeFacadeImpl = new Ds2PlayTypeFacadeImpl();
		Ds3PlayTypeFacadeImpl ds3PlayTypeFacadeImpl = new Ds3PlayTypeFacadeImpl();
		Ds4PlayTypeFacadeImpl ds4PlayTypeFacadeImpl = new Ds4PlayTypeFacadeImpl();
		Ds5PlayTypeFacadeImpl ds5PlayTypeFacadeImpl = new Ds5PlayTypeFacadeImpl();
		
		
		Dx1PlayTypeFacadeImpl dx1PlayTypeFacadeImpl = new Dx1PlayTypeFacadeImpl();
		Dx2PlayTypeFacadeImpl dx2PlayTypeFacadeImpl = new Dx2PlayTypeFacadeImpl();
		Dx3PlayTypeFacadeImpl dx3PlayTypeFacadeImpl = new Dx3PlayTypeFacadeImpl();
		Dx4PlayTypeFacadeImpl dx4PlayTypeFacadeImpl = new Dx4PlayTypeFacadeImpl();
		Dx5PlayTypeFacadeImpl dx5PlayTypeFacadeImpl = new Dx5PlayTypeFacadeImpl();
		
		
		EleIn5QszxPlayTypeFacadeImpl eleIn5PlayTypeFacadeImpl = new EleIn5QszxPlayTypeFacadeImpl();
		EleIn5QszxDsPlayTypeFacadeImpl eleIn5QszxDsPlayTypeFacadeImpl = new EleIn5QszxDsPlayTypeFacadeImpl();
		EleIn5QszuxZlPlayTypeFacadeImpl eleIn5QszuxPlayTypeFacadeImpl = new EleIn5QszuxZlPlayTypeFacadeImpl();
		EleIn5QszuxZlDsPlayTypeFacadeImpl eleIn5QszuxZlDsPlayTypeFacadeImpl = new EleIn5QszuxZlDsPlayTypeFacadeImpl();
		EleIn5Q2zxPlayTypeFacadeImpl eleIn5Wxq2PlayTypeFacadeImpl = new EleIn5Q2zxPlayTypeFacadeImpl();
		EleIn5Q2zxDsPlayTypeFacadeImpl eleIn5Q2zxDsPlayTypeFacadeImpl = new EleIn5Q2zxDsPlayTypeFacadeImpl();
		EleIn5Q2ZuxPlayTypeFacadeImpl eleIn5Wxh2ZxPlayTypeFacadeImpl = new EleIn5Q2ZuxPlayTypeFacadeImpl();
		EleIn5Q2zuxDsPlayTypeFacadeImpl eleIn5Q2zuxDsPlayTypeFacadeImpl = new EleIn5Q2zuxDsPlayTypeFacadeImpl();
		EleIn5BdwQsPlayTypeFacadeImpl eleIn5BdwQsPlayTypeFacadeImpl = new EleIn5BdwQsPlayTypeFacadeImpl();
		EleIn5DwdPlayTypeFacadeImpl eleIn5DwdPlayTypeFacadeImpl = new EleIn5DwdPlayTypeFacadeImpl();
		EleIn5QwDsPlayTypeFacadeImpl eleIn5QwDsPlayTypeFacadeImpl = new EleIn5QwDsPlayTypeFacadeImpl();
		EleIn5QwZwPlayTypeFacadeImpl eleIn5QwZwPlayTypeFacadeImpl = new EleIn5QwZwPlayTypeFacadeImpl();
		EleIn5Rx1PlayTypeFacadeImpl eleIn5Rx1PlayTypeFacadeImpl = new EleIn5Rx1PlayTypeFacadeImpl();
		EleIn5Rx1DsPlayTypeFacadeImpl eleIn5Rx1DsPlayTypeFacadeImpl = new EleIn5Rx1DsPlayTypeFacadeImpl();
		EleIn5Rx2PlayTypeFacadeImpl eleIn5Rx2PlayTypeFacadeImpl = new EleIn5Rx2PlayTypeFacadeImpl();
		EleIn5Rx2DsPlayTypeFacadeImpl eleIn5Rx2DsPlayTypeFacadeImpl = new EleIn5Rx2DsPlayTypeFacadeImpl();
		EleIn5Rx3PlayTypeFacadeImpl eleIn5Rx3PlayTypeFacadeImpl = new EleIn5Rx3PlayTypeFacadeImpl();
		EleIn5Rx3DsPlayTypeFacadeImpl eleIn5Rx3DsPlayTypeFacadeImpl = new EleIn5Rx3DsPlayTypeFacadeImpl();
		EleIn5Rx4PlayTypeFacadeImpl eleIn5Rx4PlayTypeFacadeImpl = new EleIn5Rx4PlayTypeFacadeImpl();
		EleIn5Rx4DsPlayTypeFacadeImpl eleIn5Rx4DsPlayTypeFacadeImpl = new EleIn5Rx4DsPlayTypeFacadeImpl();
		EleIn5Rx5PlayTypeFacadeImpl eleIn5Rx5PlayTypeFacadeImpl = new EleIn5Rx5PlayTypeFacadeImpl();
		EleIn5Rx5DsPlayTypeFacadeImpl eleIn5Rx5DsPlayTypeFacadeImpl = new EleIn5Rx5DsPlayTypeFacadeImpl();
		EleIn5Rx6PlayTypeFacadeImpl eleIn5Rx6PlayTypeFacadeImpl = new EleIn5Rx6PlayTypeFacadeImpl();
		EleIn5Rx6DsPlayTypeFacadeImpl eleIn5Rx6DsPlayTypeFacadeImpl = new EleIn5Rx6DsPlayTypeFacadeImpl();
		EleIn5Rx7PlayTypeFacadeImpl eleIn5Rx7PlayTypeFacadeImpl = new EleIn5Rx7PlayTypeFacadeImpl();
		EleIn5Rx7DsPlayTypeFacadeImpl eleIn5Rx7DsPlayTypeFacadeImpl = new EleIn5Rx7DsPlayTypeFacadeImpl();
		EleIn5Rx8PlayTypeFacadeImpl eleIn5Rx8PlayTypeFacadeImpl = new EleIn5Rx8PlayTypeFacadeImpl();
		EleIn5Rx8DsPlayTypeFacadeImpl eleIn5Rx8DsPlayTypeFacadeImpl = new EleIn5Rx8DsPlayTypeFacadeImpl();
		Pk10QyzxPlayTypeFacadeImpl pk10QyzxPlayTypeFacadeImpl = new Pk10QyzxPlayTypeFacadeImpl();
		Pk10QezxPlayTypeFacadeImpl pk10QezxPlayTypeFacadeImpl = new Pk10QezxPlayTypeFacadeImpl();
		Pk10QszxPlayTypeFacadeImpl pk10QszxPlayTypeFacadeImpl = new Pk10QszxPlayTypeFacadeImpl();
		Pk10DwdPlayTypeFacadeImpl pk10DwdPlayTypeFacadeImpl = new Pk10DwdPlayTypeFacadeImpl();
		Pk10Lh1V10PlayTypeFacadeImpl pk10Lh1V10PlayTypeFacadeImpl = new Pk10Lh1V10PlayTypeFacadeImpl();
		Pk10Lh2V9PlayTypeFacadeImpl pk10Lh2V9PlayTypeFacadeImpl = new Pk10Lh2V9PlayTypeFacadeImpl();
		Pk10Lh3V8PlayTypeFacadeImpl pk10Lh3V8PlayTypeFacadeImpl = new Pk10Lh3V8PlayTypeFacadeImpl();
		Pk10Lh4V7PlayTypeFacadeImpl pk10Lh4V7PlayTypeFacadeImpl = new Pk10Lh4V7PlayTypeFacadeImpl();
		Pk10Lh5V6PlayTypeFacadeImpl pk10Lh5V6PlayTypeFacadeImpl = new Pk10Lh5V6PlayTypeFacadeImpl();
		Pk10Ds1PlayTypeFacadeImpl pk10Ds1PlayTypeFacadeImpl = new Pk10Ds1PlayTypeFacadeImpl();
		Pk10Ds2PlayTypeFacadeImpl pk10Ds2PlayTypeFacadeImpl = new Pk10Ds2PlayTypeFacadeImpl();
		Pk10Ds3PlayTypeFacadeImpl pk10Ds3PlayTypeFacadeImpl = new Pk10Ds3PlayTypeFacadeImpl();
		
		Pk10Ds4PlayTypeFacadeImpl pk10Ds4PlayTypeFacadeImpl = new Pk10Ds4PlayTypeFacadeImpl();
		Pk10Ds5PlayTypeFacadeImpl pk10Ds5PlayTypeFacadeImpl = new Pk10Ds5PlayTypeFacadeImpl();
		Pk10Ds6PlayTypeFacadeImpl pk10Ds6PlayTypeFacadeImpl = new Pk10Ds6PlayTypeFacadeImpl();
		Pk10Ds7PlayTypeFacadeImpl pk10Ds7PlayTypeFacadeImpl = new Pk10Ds7PlayTypeFacadeImpl();
		Pk10Ds8PlayTypeFacadeImpl pk10Ds8PlayTypeFacadeImpl = new Pk10Ds8PlayTypeFacadeImpl();
		Pk10Ds9PlayTypeFacadeImpl pk10Ds9PlayTypeFacadeImpl = new Pk10Ds9PlayTypeFacadeImpl();
		Pk10Ds10PlayTypeFacadeImpl pk10Ds10PlayTypeFacadeImpl = new Pk10Ds10PlayTypeFacadeImpl();
		
		Pk10Dx1PlayTypeFacadeImpl pk10Dx1PlayTypeFacadeImpl = new Pk10Dx1PlayTypeFacadeImpl();
		Pk10Dx2PlayTypeFacadeImpl pk10Dx2PlayTypeFacadeImpl = new Pk10Dx2PlayTypeFacadeImpl();
		Pk10Dx3PlayTypeFacadeImpl pk10Dx3PlayTypeFacadeImpl = new Pk10Dx3PlayTypeFacadeImpl();
		Pk10Dx4PlayTypeFacadeImpl pk10Dx4PlayTypeFacadeImpl = new Pk10Dx4PlayTypeFacadeImpl();
		Pk10Dx5PlayTypeFacadeImpl pk10Dx5PlayTypeFacadeImpl = new Pk10Dx5PlayTypeFacadeImpl();
		Pk10Dx6PlayTypeFacadeImpl pk10Dx6PlayTypeFacadeImpl = new Pk10Dx6PlayTypeFacadeImpl();
		Pk10Dx7PlayTypeFacadeImpl pk10Dx7PlayTypeFacadeImpl = new Pk10Dx7PlayTypeFacadeImpl();
		Pk10Dx8PlayTypeFacadeImpl pk10Dx8PlayTypeFacadeImpl = new Pk10Dx8PlayTypeFacadeImpl();
		Pk10Dx9PlayTypeFacadeImpl pk10Dx9PlayTypeFacadeImpl = new Pk10Dx9PlayTypeFacadeImpl();
		Pk10Dx10PlayTypeFacadeImpl pk10Dx10PlayTypeFacadeImpl = new Pk10Dx10PlayTypeFacadeImpl();
		
		BdwQyPlayTypeFacadeImpl tc3BdwQyPlayTypeFacadeImpl = new BdwQyPlayTypeFacadeImpl();
		EwhsBgDsPlayTypeFacadeImpl tc3EwhsBgDsPlayTypeFacadeImpl = new EwhsBgDsPlayTypeFacadeImpl();
		EwhsBsDsPlayTypeFacadeImpl tc3EwhsBsDsPlayTypeFacadeImpl = new EwhsBsDsPlayTypeFacadeImpl();
		EwhsSgDsPlayTypeFacadeImpl tc3EwhsSgDsPlayTypeFacadeImpl = new EwhsSgDsPlayTypeFacadeImpl();
		SwhsDsPlayTypeFacadeImpl tc3SwhsDsPlayTypeFacadeImpl = new SwhsDsPlayTypeFacadeImpl();
		SwhsDxPlayTypeFacadeImpl tc3SwhsDxPlayTypeFacadeImpl = new SwhsDxPlayTypeFacadeImpl();
		
		BwDsPlayTypeFacadeImpl tc3BwDsPlayTypeFacadeImpl = new BwDsPlayTypeFacadeImpl();
		BwDxPlayTypeFacadeImpl tc3BwDxPlayTypeFacadeImpl = new BwDxPlayTypeFacadeImpl();
		BwZhPlayTypeFacadeImpl tc3BwZhPlayTypeFacadeImpl = new BwZhPlayTypeFacadeImpl();
		
		SwDsPlayTypeFacadeImpl tc3SwDsPlayTypeFacadeImpl = new SwDsPlayTypeFacadeImpl();
		SwDxPlayTypeFacadeImpl tc3SwDxPlayTypeFacadeImpl = new SwDxPlayTypeFacadeImpl();
		SwZhPlayTypeFacadeImpl tc3SwZhPlayTypeFacadeImpl = new SwZhPlayTypeFacadeImpl();
		
		GwDsPlayTypeFacadeImpl tc3GwDsPlayTypeFacadeImpl = new GwDsPlayTypeFacadeImpl();
		GwDxPlayTypeFacadeImpl tc3GwDxPlayTypeFacadeImpl = new GwDxPlayTypeFacadeImpl();
		GwZhPlayTypeFacadeImpl tc3GwZhPlayTypeFacadeImpl = new GwZhPlayTypeFacadeImpl();
		
		BwSZPlayTypeFacadeImpl tc3BwSZPlayTypeFacadeImpl = new BwSZPlayTypeFacadeImpl();
		SwSZPlayTypeFacadeImpl tc3SwSZPlayTypeFacadeImpl = new SwSZPlayTypeFacadeImpl();
		GwSZPlayTypeFacadeImpl tc3GwSZPlayTypeFacadeImpl = new GwSZPlayTypeFacadeImpl();
		
		
		EwDwBsSZPlayTypeFacadeImpl tc3EwDwBsSZPlayTypeFacadeImpl = new EwDwBsSZPlayTypeFacadeImpl();
		EwDwBgSZPlayTypeFacadeImpl tc3EwDwBgSZPlayTypeFacadeImpl = new EwDwBgSZPlayTypeFacadeImpl();
		EwDwSgSZPlayTypeFacadeImpl tc3EwDwSgSZPlayTypeFacadeImpl = new EwDwSgSZPlayTypeFacadeImpl();
		
		SwDwSZPlayTypeFacadeImpl tc3SwDwSZPlayTypeFacadeImpl = new SwDwSZPlayTypeFacadeImpl();
		
		BdwEwPlayTypeFacadeImpl tc3BdwEwPlayTypeFacadeImpl = new BdwEwPlayTypeFacadeImpl();
		
		EwhsBsSzPlayTypeFacadeImpl tc3EwhsBsSzPlayTypeFacadeImpl = new EwhsBsSzPlayTypeFacadeImpl();
		EwhsBgSzPlayTypeFacadeImpl tc3EwhsBgSzPlayTypeFacadeImpl = new EwhsBgSzPlayTypeFacadeImpl();
		EwhsSgSzPlayTypeFacadeImpl tc3EwhsSgSzPlayTypeFacadeImpl = new EwhsSgSzPlayTypeFacadeImpl();
		
		SwhsSzPlayTypeFacadeImpl tc3SwhsSzPlayTypeFacadeImpl = new SwhsSzPlayTypeFacadeImpl();
		
		BdwSwPlayTypeFacadeImpl tc3BdwSwPlayTypeFacadeImpl = new BdwSwPlayTypeFacadeImpl();
		
		playTypeFacades.add(qszxPlayTypeFacadeImpl);
		playTypeFacades.add(qszxDsPlayTypeFacadeImpl);
		playTypeFacades.add(zszxPlayTypeFacadeImpl);
		playTypeFacades.add(zszxDsPlayTypeFacadeImpl);
		playTypeFacades.add(hszxPlayTypeFacadeImpl);
		playTypeFacades.add(hszxDsPlayTypeFacadeImpl);
		playTypeFacades.add(qszuxZsPlayTypeFacadeImpl);
		playTypeFacades.add(qszuxZlPlayTypeFacadeImpl);
		playTypeFacades.add(qszuxMixPlayTypeFacadeImpl);
		playTypeFacades.add(zszuxZsPlayTypeFacadeImpl);
		playTypeFacades.add(zszuxZlPlayTypeFacadeImpl);
		playTypeFacades.add(zszuxHhzxPlayTypeFacadeImpl);
		playTypeFacades.add(hszuxZsPlayTypeFacadeImpl);
		playTypeFacades.add(hszuxZlPlayTypeFacadeImpl);
		playTypeFacades.add(hszuxHhzxPlayTypeFacadeImpl);
		playTypeFacades.add(wxq2PlayTypeFacadeImpl);
		playTypeFacades.add(wxq2DsPlayTypeFacadeImpl);
		playTypeFacades.add(wxq2ZxPlayTypeFacadeImpl);
		playTypeFacades.add(wxh2PlayTypeFacadeImpl);
		playTypeFacades.add(wxh2DsPlayTypeFacadeImpl);
		playTypeFacades.add(wxh2ZxPlayTypeFacadeImpl);
		playTypeFacades.add(bdwQsPlayTypeFacadeImpl);
		playTypeFacades.add(bdwZsPlayTypeFacadeImpl);
		playTypeFacades.add(bdwHsPlayTypeFacadeImpl);
		playTypeFacades.add(dwdPlayTypeFacadeImpl);

		playTypeFacades.add(ds1PlayTypeFacadeImpl);
		playTypeFacades.add(ds2PlayTypeFacadeImpl);
		playTypeFacades.add(ds3PlayTypeFacadeImpl);
		playTypeFacades.add(ds4PlayTypeFacadeImpl);
		playTypeFacades.add(ds5PlayTypeFacadeImpl);
		
		playTypeFacades.add(dx1PlayTypeFacadeImpl);
		playTypeFacades.add(dx2PlayTypeFacadeImpl);
		playTypeFacades.add(dx3PlayTypeFacadeImpl);
		playTypeFacades.add(dx4PlayTypeFacadeImpl);
		playTypeFacades.add(dx5PlayTypeFacadeImpl);
		
		playTypeFacades.add(eleIn5PlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5QszxDsPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5QszuxZlDsPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5QszuxPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Q2zxDsPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Wxq2PlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Q2zuxDsPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Wxh2ZxPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5BdwQsPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5DwdPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5QwDsPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5QwZwPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx1DsPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx1PlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx2PlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx2DsPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx3PlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx3DsPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx4PlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx4DsPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx5PlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx5DsPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx6PlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx6DsPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx7PlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx7DsPlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx8PlayTypeFacadeImpl);
		playTypeFacades.add(eleIn5Rx8DsPlayTypeFacadeImpl);
		playTypeFacades.add(pk10QyzxPlayTypeFacadeImpl);
		playTypeFacades.add(pk10QezxPlayTypeFacadeImpl);
		playTypeFacades.add(pk10QszxPlayTypeFacadeImpl);
		playTypeFacades.add(pk10DwdPlayTypeFacadeImpl);
		playTypeFacades.add(pk10Lh1V10PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Lh2V9PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Lh3V8PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Lh4V7PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Lh5V6PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Ds1PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Ds2PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Ds3PlayTypeFacadeImpl);
		
		playTypeFacades.add(pk10Ds4PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Ds5PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Ds6PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Ds7PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Ds8PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Ds9PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Ds10PlayTypeFacadeImpl);
		
		playTypeFacades.add(pk10Dx1PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Dx2PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Dx3PlayTypeFacadeImpl);		
		playTypeFacades.add(pk10Dx4PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Dx5PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Dx6PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Dx7PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Dx8PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Dx9PlayTypeFacadeImpl);
		playTypeFacades.add(pk10Dx10PlayTypeFacadeImpl);
		
		playTypeFacades.add(tc3BdwQyPlayTypeFacadeImpl);
		playTypeFacades.add(tc3EwhsBsDsPlayTypeFacadeImpl);
		playTypeFacades.add(tc3EwhsBgDsPlayTypeFacadeImpl);
		playTypeFacades.add(tc3EwhsSgDsPlayTypeFacadeImpl);
		playTypeFacades.add(tc3SwhsDsPlayTypeFacadeImpl);
		playTypeFacades.add(tc3SwhsDxPlayTypeFacadeImpl);
		
		
		
		
		playTypeFacades.add(tc3BwDsPlayTypeFacadeImpl);
		playTypeFacades.add(tc3BwDxPlayTypeFacadeImpl);
		playTypeFacades.add(tc3BwZhPlayTypeFacadeImpl);
		playTypeFacades.add(tc3SwDsPlayTypeFacadeImpl);
		playTypeFacades.add(tc3SwDxPlayTypeFacadeImpl);
		playTypeFacades.add(tc3SwZhPlayTypeFacadeImpl);
		
		playTypeFacades.add(tc3GwDsPlayTypeFacadeImpl);
		playTypeFacades.add(tc3GwDxPlayTypeFacadeImpl);
		playTypeFacades.add(tc3GwZhPlayTypeFacadeImpl);
		
		
		playTypeFacades.add(tc3BwSZPlayTypeFacadeImpl);
		playTypeFacades.add(tc3SwSZPlayTypeFacadeImpl);
		playTypeFacades.add(tc3GwSZPlayTypeFacadeImpl);
		
		playTypeFacades.add(tc3EwDwBsSZPlayTypeFacadeImpl);
		playTypeFacades.add(tc3EwDwBgSZPlayTypeFacadeImpl);
		playTypeFacades.add(tc3EwDwSgSZPlayTypeFacadeImpl);
		
		playTypeFacades.add(tc3SwDwSZPlayTypeFacadeImpl);
		
		playTypeFacades.add(tc3BdwEwPlayTypeFacadeImpl);
		
		playTypeFacades.add(tc3EwhsBsSzPlayTypeFacadeImpl);
		playTypeFacades.add(tc3EwhsBgSzPlayTypeFacadeImpl);
		playTypeFacades.add(tc3EwhsSgSzPlayTypeFacadeImpl);
		
		playTypeFacades.add(tc3SwhsSzPlayTypeFacadeImpl);
		
		playTypeFacades.add(tc3BdwSwPlayTypeFacadeImpl);
	}
	
	public static PlayTypeFactory getInstance() {
		if(factory == null) {
			factory = new PlayTypeFactory();
		}
		
		return factory;
	}
	
	public PlayTypeFacade getPlayTypeFacade(String facadeName) {
		for(PlayTypeFacade playTypeFacade : playTypeFacades) {
			if(playTypeFacade.getPlayTypeDesc().equals(facadeName)) {
				return playTypeFacade;
			}
		}
		
		return null;
	}
}
