/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import java.io.Serializable;

/**
 *
 * @author Mirko
 */
public enum Operations implements Serializable {
    LOGIN,
    LOGOUT,
    GET_ALL_EXHIBITORS,
    GET_ALL_MANIFESTATIONS,
    GET_ALL_DAILY_SCHEDULES,
    GET_ALL_DAILY_SCHEDULE_EXHIBITORS,
    GET_DAILY_SCHEDULE_EXHIBITORS_WITH_CONDITION,
    GET_ALL_COMPANIES,
    GET_ALL_MANIFESTATION_TYPES,
    GET_ALL_HALLS,
    ADD_EXHIBITOR,
    EDIT_EXHIBITOR,
    DELETE_EXHIBITOR,
    ADD_MANIFESTATION,
    EDIT_MANIFESTATION,
    DELETE_MANIFESTATION,
    ADD_DAILY_SCHEDULE,
    EDIT_DAILY_SCHEDULE,
    GET_DAILY_SCHEDULE,
    DELETE_DAILY_SCHEDULE,
    ADD_DAILY_SCHEDULE_EXHIBITORS,
    EDIT_DAILY_SCHEDULE_EXHIBITORS,
    DELETE_DAILY_SCHEDULE_EXHIBITORS
}
