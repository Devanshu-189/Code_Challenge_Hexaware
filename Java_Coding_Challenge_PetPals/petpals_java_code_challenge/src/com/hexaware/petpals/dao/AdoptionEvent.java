package com.hexaware.petpals.dao;

import java.util.ArrayList;
import java.util.List;

import com.hexaware.petpals.services.IAdoptable;

public class AdoptionEvent implements IAdoptable{
	
    private List<IAdoptable> participants;
    


    public AdoptionEvent() {
        participants = new ArrayList<>();
    }

    public void registerParticipant(IAdoptable participant) {
        participants.add(participant);
        System.out.println("Participant registered: " + participant);
    }

    public void hostEvent() {
        System.out.println("Adoption Event is starting!");
        for (IAdoptable participant : participants) {
            participant.adopt();
        }
        System.out.println("Adoption Event has ended!");
    }

	@Override
	public void adopt() {}



}

